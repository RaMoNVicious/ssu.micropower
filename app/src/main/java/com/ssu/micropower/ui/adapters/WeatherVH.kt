package com.ssu.micropower.ui.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.micropower.databinding.ListItemWeatherBinding

class WeatherVH(binding: ListItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
    val txtDate: TextView = binding.txtDate
    val txtTemperature: TextView = binding.txtTemperature
    val txtPrecipitation: TextView = binding.txtPrecipitation
    val txtWindSpeed: TextView = binding.txtWindSpeed
    val imgWindDirection: ImageView = binding.imgWindDirection
}