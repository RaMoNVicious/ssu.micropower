package com.ssu.micropower.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssu.micropower.databinding.ListItemGenerationBatteryBinding
import com.ssu.micropower.databinding.ListItemGenerationSolarBinding
import com.ssu.micropower.databinding.ListItemGenerationWindBinding
import com.ssu.micropower.models.domain.GenerationItem
import com.ssu.micropower.models.domain.GenerationItemType

class GenerationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _items = emptyList<GenerationItem>()

    fun setItems(items: List<GenerationItem>) {
        _items = items
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (_items[position].type) {
            GenerationItemType.Solar -> VIEW_SOLAR
            GenerationItemType.Wind -> VIEW_WIND
            GenerationItemType.Battery -> VIEW_BATTERY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_SOLAR ->
                GenerationSolarVH(
                    ListItemGenerationSolarBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            VIEW_WIND ->
                GenerationWindVH(
                    ListItemGenerationWindBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )

            else ->
                GenerationBatteryVH(
                    ListItemGenerationBatteryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
        }
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GenerationVH) {
            holder.setup(_items[position])
        }
    }


    companion object {
        private const val VIEW_SOLAR = 0
        private const val VIEW_WIND = 1
        private const val VIEW_BATTERY = 2
    }
}