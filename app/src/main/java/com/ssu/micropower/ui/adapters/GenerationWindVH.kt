package com.ssu.micropower.ui.adapters

import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ssu.micropower.R
import com.ssu.micropower.databinding.ListItemGenerationWindBinding
import com.ssu.micropower.models.asDeviceName
import com.ssu.micropower.models.domain.GenerationItem
import com.ssu.micropower.models.domain.GenerationItemType
import com.ssu.micropower.models.toDate
import java.text.SimpleDateFormat

class GenerationWindVH(binding: ListItemGenerationWindBinding) : GenerationVH(binding.root) {
    private val txtMaxPower: TextView = binding.txtPowerMax
    private val txtDateCurrent: TextView = binding.txtDateCurrent
    private val txtValueCurrent: TextView = binding.txtValueCurrent
    private val prbValueCurrent: ProgressBar = binding.prbValueCurrent
    private val txtConditionCurrent: TextView = binding.txtConditionCurrent
    private val txtDateForecast: TextView = binding.txtDateForecast
    private val txtValueForecast: TextView = binding.txtValueForecast
    private val prbValueForecast: ProgressBar = binding.prbValueForecast
    private val txtConditionForecast: TextView = binding.txtConditionCurrent
    private val pnlDevices: ChipGroup = binding.pnlDevices

    override fun setup(generationItem: GenerationItem) {
        val context = itemView.context

        generationItem
            .takeIf { it.type == GenerationItemType.Wind }
            ?.let {
                txtMaxPower.text = context.getString(R.string.value_kwh, it.maxPower)

                txtDateCurrent.text = SimpleDateFormat(context.getString(R.string.date_time_normal))
                    .format(it.valueCurrent.time.toDate())
                txtValueCurrent.text = context.getString(
                    R.string.value_kwh,
                    it.valueCurrent.value
                )
                prbValueCurrent.setProgress(
                    (100f * it.valueCurrent.value / it.maxPower).toInt(),
                    true
                )
                txtConditionCurrent.text = context.getString(
                    R.string.weather_wind_speed_value,
                    it.valueCurrent.conditions
                )


                txtDateForecast.text = SimpleDateFormat(context.getString(R.string.date_time_normal))
                    .format(it.valueForecast.time.toDate())
                txtValueForecast.text = context.getString(
                    R.string.value_kwh,
                    it.valueForecast.value
                )
                prbValueForecast.setProgress(
                    (100f * it.valueForecast.value / it.maxPower).toInt(),
                    true
                )
                txtConditionForecast.text = context.getString(
                    R.string.weather_wind_speed_value,
                    it.valueForecast.conditions
                )

                pnlDevices.removeAllViews()
                val inflater = LayoutInflater.from(pnlDevices.context)
                it.devices.forEach { device ->
                    val chip = inflater.inflate(
                        R.layout.chip_device,
                        pnlDevices,
                        false
                    ) as Chip

                    chip.text = context.getString(
                        R.string.generator_device_item,
                        device.count,
                        device.name.asDeviceName()
                    )
                    pnlDevices.addView(chip)
                }
            }
    }
}