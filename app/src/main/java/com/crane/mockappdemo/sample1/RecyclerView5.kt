package com.crane.mockappdemo.sample1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.crane.mockapp.annotations.MockAppLayout
import com.crane.mockapp.annotations.MockAppView
import com.crane.mockapp.core.MockApp
import com.crane.mockapp.core.MockAppActivity
import com.crane.mockapp.core.MockAppViewBinder

@MockAppLayout(projectName = "icountries", layoutName = "page_news")
class RecyclerView5 : MockAppActivity() {

    @MockAppView
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView.adapter = MyAdapter(this)
    }
}

class MyAdapter(
    val context: Context
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    abstract inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(position: Int)
    }

    @MockAppLayout(projectName = "icountries", layoutName = "page_fav_item")
    inner class MyViewHolder1(itemView: View) : MyViewHolder(itemView), MockAppViewBinder {

        @MockAppView
        lateinit var titleText: TextView

        @MockAppView
        lateinit var subtitleText: TextView

        override fun onViewsReady() {
            subtitleText.setOnClickListener {
                Toast.makeText(context, "What do you want?", Toast.LENGTH_LONG)
            }
        }

        override fun bind(position: Int) {
            titleText.text = "Some item att position $position"
            subtitleText.text = "Square of position is ${position * position}"
        }
    }

    @MockAppLayout(projectName = "icountries", layoutName = "page_news_item")
    inner class MyViewHolder2(itemView: View) : MyViewHolder(itemView) {

        @MockAppView
        lateinit var shortText: TextView

        @MockAppView
        lateinit var fullText: TextView

        override fun bind(position: Int) {
            shortText.text = "Some item att position $position"
            fullText.text = "Cos of position is ${Math.cos(position.toDouble())}"
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position % 2) {
            0 -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            0 -> MockApp.createViewHolder(context, this, MyViewHolder1::class.java, parent)
            else -> MockApp.createViewHolder(context, this, MyViewHolder2::class.java, parent)
        }
    }

    override fun getItemCount(): Int {
        return 30
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(position)
    }
}