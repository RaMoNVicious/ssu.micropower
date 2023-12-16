package com.ssu.micropower.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssu.micropower.R
import com.ssu.micropower.databinding.ListItemLocationBinding
import com.ssu.micropower.models.domain.Location

class LocationsAdapter : RecyclerView.Adapter<LocationVH>() {

    interface OnClickListener {
        fun onClick(location: Location)
    }

    private var _items = emptyList<Location>()

    private var _onClickListener: OnClickListener? = null

    fun setItems(items: List<Location>, onClickListener: OnClickListener) {
        _items = items
        _onClickListener = onClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVH {
        return LocationVH(
            ListItemLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        val context = holder.itemView.context
        val item = _items[position]

        holder.txtName.text = item.name
        holder.txtType.text = item.typeName
        holder.txtAddress.text = context.getString(
            R.string.location_address_value,
            item.city.name,
            item.country.name
        )
        holder.txtCoordinates.text = context.getString(
            R.string.location_coordinates_value,
            item.coordinates.latitude,
            item.coordinates.longitude
        )
        holder.itemView.setOnClickListener {
            _onClickListener?.onClick(item)
        }
    }
}