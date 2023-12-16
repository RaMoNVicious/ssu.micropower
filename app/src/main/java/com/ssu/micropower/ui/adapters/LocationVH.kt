package com.ssu.micropower.ui.adapters

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssu.micropower.databinding.ListItemLocationBinding

class LocationVH(binding: ListItemLocationBinding) : RecyclerView.ViewHolder(binding.root) {
    val txtName: TextView = binding.txtName
    val txtType: TextView = binding.txtType
    val txtAddress: TextView = binding.txtAddress
    val txtCoordinates: TextView = binding.txtCoordinates
}