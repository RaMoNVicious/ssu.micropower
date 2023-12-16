package com.ssu.micropower.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.ssu.micropower.R
import com.ssu.micropower.databinding.FragmentMainBinding
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.ui.adapters.GenerationAdapter
import com.ssu.micropower.ui.adapters.WeatherAdapter
import com.ssu.micropower.ui.location.LocationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class MainFragment : Fragment() {

    private lateinit var _binding: FragmentMainBinding

    private val viewModel: MainViewModel by viewModel()

    private lateinit var adapter: GenerationAdapter

    private lateinit var adapterWeather: WeatherAdapter

    private var isInit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GenerationAdapter()
        adapterWeather = WeatherAdapter()

        _binding.apply {
            lifecycleOwner = this@MainFragment

            lstComponents.adapter = adapter
            LinearSnapHelper().attachToRecyclerView(lstComponents)

            lstWeather.adapter = adapterWeather
            LinearSnapHelper().attachToRecyclerView(lstWeather)

            viewModel.message.observe(viewLifecycleOwner) {
                isLoading = false
                isLoadingWeather = false
                Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
            }

            viewModel.locations.observe(viewLifecycleOwner) { locations ->
                println(locations)

                locations
                    .takeIf { it.isNotEmpty() }
                    ?.first()
                    ?.let {
                        viewModel.getLocationStructure(it)
                        viewModel.getWeather(it)
                        isInit = true
                    }
            }

            viewModel.locationInfo.observe(viewLifecycleOwner) { locationInfo ->
                btnLocations.text = locationInfo.location.name
                txtTime.text = SimpleDateFormat(getString(R.string.date_time_normal)).format(locationInfo.time)
                txtConsumption.text = getString(R.string.value_kwh, locationInfo.consumption)
                txtSell.text = getString(R.string.value_kwh, locationInfo.sell)
                txtPurchase.text = getString(R.string.value_kwh, locationInfo.purchase)

                adapter.setItems(locationInfo.generation)
                lstComponents.smoothScrollToPosition(0)

                isLoading = false
            }

            viewModel.weather.observe(viewLifecycleOwner) {
                it.current.apply {
                    txtWeatherTemp.text = getString(R.string.weather_temp_value, temperature)
                    txtWeatherPrecipitation.text = getString(R.string.weather_precipitation_value, precipitation)
                    txtWeatherPressure.text = getString(R.string.weather_pressure_value, pressure)
                    txtWeatherWindSpeed.text = getString(R.string.weather_wind_speed_value, windSpeed)

                    imgWindDirection.rotation = (windDirection.toFloat() / 45f).roundToInt() * 45f + 90f
                }

                adapterWeather.setItems(it.hourly)

                isLoadingWeather = false
            }

            viewModel.logout.observe(viewLifecycleOwner) {
                activity
                    ?.findNavController(R.id.nav_host_fragment_container)
                    ?.navigateUp()
            }

            btnLocations.setOnClickListener {
                setFragmentResultListener(LocationFragment.SELECT_LOCATION) { _, bundle ->
                    bundle
                        .takeIf { it.containsKey(LocationFragment.LOCATION_ITEM) }
                        ?.getSerializable(LocationFragment.LOCATION_ITEM)
                        ?.let { it as Location }
                        ?.let {
                            isLoading = true
                            isLoadingWeather = true
                            viewModel.getLocationStructure(it)
                            viewModel.getWeather(it)
                        }
                }

                activity
                    ?.findNavController(R.id.nav_host_fragment_container)
                    ?.navigate(
                        R.id.action_mainFragment_to_locationFragment
                    )
            }

            btnLogout.setOnClickListener {
                viewModel.logout()
            }
        }

        if (!isInit) {
            _binding.isLoading = true
            _binding.isLoadingWeather = true
            viewModel.getLocations()
        }
    }
}