package com.ssu.micropower.ui.adapters

import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssu.micropower.R
import com.ssu.micropower.databinding.ListItemGenerationSolarBinding
import com.ssu.micropower.models.domain.GenerationItem
import com.ssu.micropower.models.domain.GenerationItemType
import com.ssu.micropower.models.toDate
import java.text.SimpleDateFormat

class GenerationSolarVH(binding: ListItemGenerationSolarBinding) : GenerationVH(binding.root) {
    private val txtMaxPower: TextView = binding.txtPowerMax
    private val txtDateCurrent: TextView = binding.txtDateCurrent
    private val txtValueCurrent: TextView = binding.txtValueCurrent
    private val prbValueCurrent: ProgressBar = binding.prbValueCurrent
    private val imgConditionCurrent: ImageView = binding.imgConditionCurrent
    private val txtDateForecast: TextView = binding.txtDateForecast
    private val txtValueForecast: TextView = binding.txtValueForecast
    private val prbValueForecast: ProgressBar = binding.prbValueForecast
    private val imgConditionForecast: ImageView = binding.imgConditionForecast
    private val pnlDevices: ChipGroup = binding.pnlDevices

    override fun setup(generationItem: GenerationItem) {
        val context = itemView.context

        generationItem
            .takeIf { it.type == GenerationItemType.Solar }
            ?.let {
                txtMaxPower.text = context.getString(R.string.value_kwh, it.maxPower)

                txtDateCurrent.text = SimpleDateFormat(context.getString(R.string.date_time_normal))
                    .format(it.valueCurrent.time.toDate())
                txtValueCurrent.text = context.getString(R.string.value_kwh, it.valueCurrent.value)
                prbValueCurrent.setProgress(
                    (100f * it.valueCurrent.value / it.maxPower).toInt(),
                    true
                )
                imgConditionCurrent.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        getWeatherIcon(it.valueCurrent.conditions / 100f)
                    )
                )

                txtDateForecast.text =
                    SimpleDateFormat(context.getString(R.string.date_time_normal))
                        .format(it.valueForecast.time.toDate())
                txtValueForecast.text = context.getString(R.string.value_kwh, it.valueForecast.value)
                prbValueForecast.setProgress(
                    (100f * it.valueForecast.value / it.maxPower).toInt(),
                    true
                )
                imgConditionForecast.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        getWeatherIcon(it.valueForecast.conditions / 100f)
                    )
                )

                pnlDevices.removeAllViews()
                val inflater = LayoutInflater.from(pnlDevices.context)
                it.devices.forEach { device ->
                    val chip = inflater.inflate(
                        R.layout.chip_device,
                        pnlDevices,
                        false
                    ) as Chip

                    chip.text = context.getString(R.string.generator_device_item, device.count, device.name)
                    pnlDevices.addView(chip)
                }
            }
    }

    private fun getWeatherIcon(condition: Float): Int {
        return when {
            condition < 0.25f -> R.drawable.ic_weather_0
            condition < 0.5f -> R.drawable.ic_weather_1
            condition < 0.75f -> R.drawable.ic_weather_1
            else -> R.drawable.ic_weather_3
        }
    }
}