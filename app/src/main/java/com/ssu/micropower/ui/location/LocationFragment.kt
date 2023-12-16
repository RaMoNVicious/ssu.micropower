package com.ssu.micropower.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ssu.micropower.R
import com.ssu.micropower.databinding.FragmentLocationBinding
import com.ssu.micropower.models.domain.Location
import com.ssu.micropower.ui.adapters.LocationsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationFragment : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentLocationBinding

    private val viewModel: LocationViewModel by viewModel()

    private lateinit var adapter: LocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetPopupTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationsAdapter()

        _binding.apply {
            lifecycleOwner = this@LocationFragment

            lstLocations.adapter = adapter

            viewModel.locations.observe(viewLifecycleOwner) { locations ->
                adapter.setItems(
                    locations,
                    object : LocationsAdapter.OnClickListener {
                        override fun onClick(location: Location) {
                            setFragmentResult(
                                SELECT_LOCATION,
                                bundleOf(
                                    LOCATION_ITEM to location
                                )
                            )
                            dismiss()
                        }
                    }
                )
            }
        }

        viewModel.getLocations()
    }

    companion object {
        const val SELECT_LOCATION = "select_location"
        const val LOCATION_ITEM = "location_item"
    }
}