package com.ssu.micropower.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ssu.micropower.models.domain.GenerationItem

abstract class GenerationVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setup(generationItem: GenerationItem)
}