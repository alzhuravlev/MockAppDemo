package com.crane.mockappdemo.main

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockApp
import com.crane.mockapp.core.MockAppViewBinder

class MainAdapter(
    val context: Context
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    @MockAppLayout(projectName = "mockappdemo", layoutName = "main_item")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), MockAppViewBinder {

        @MockAppView
        lateinit var name: TextView
        @MockAppView
        lateinit var description: TextView

        override fun onViewsReady() {
            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos == RecyclerView.NO_POSITION)
                    return@setOnClickListener
                val item = items[pos]
                context.startActivity(Intent(context, item.activity))
            }
        }

        fun bind(item: MainItem, position: Int) {
            name.text = "${item.activity.simpleName}.kt"
            description.text = item.decription
        }
    }

    private val items = mutableListOf<MainItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MockApp.createViewHolder(context, this, ViewHolder::class.java, parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun update(items: List<MainItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}