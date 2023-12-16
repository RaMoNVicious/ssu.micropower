package com.ssu.micropower.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssu.micropower.R
import com.ssu.micropower.app.Constants
import com.ssu.micropower.databinding.ListItemWeatherBinding
import com.ssu.micropower.models.domain.WeatherHourly
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt

class WeatherAdapter : RecyclerView.Adapter<WeatherVH>() {

    private var _items = emptyList<WeatherHourly>()

    fun setItems(items: List<WeatherHourly>) {
        _items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherVH {
        val width = parent.width / Constants.WEATHER_ITEMS_COUNT
        return WeatherVH(
            ListItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.layoutParams.width = width
        }
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: WeatherVH, position: Int) {
        val item = _items[position]
        val context = holder.itemView.context

        holder.apply {
            txtDate.text = SimpleDateFormat(
                context.getString(R.string.date_time_short)
            ).format(item.time)

            txtTemperature.text = context.getString(
                R.string.weather_temp_range_value,
                item.temperatureMin.roundToInt(),
                item.temperatureMax.roundToInt()
            )

            txtPrecipitation.text = context.getString(
                R.string.weather_precipitation_probability_value,
                item.precipitationProbability
            )

            txtWindSpeed.text = context.getString(
                R.string.weather_wind_speed_value,
                item.windSpeed
            )

            imgWindDirection.rotation = (item.windDirection.toFloat() / 45f).roundToInt() * 45f + 90f
        }
    }
}