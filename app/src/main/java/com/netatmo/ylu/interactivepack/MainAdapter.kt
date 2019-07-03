package com.netatmo.ylu.interactivepack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.netatmo.ylu.interactivepack.coordinator.CardData

class MainAdapter(val context: Context) : RecyclerView.Adapter<MainAdapter.CardHolder>() {

    private var data = ArrayList<CardData>()

    fun setData(data: ArrayList<CardData>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_card_view, parent, false)
        return CardHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.titleText.text = data[position].title
        holder.itemView.setOnClickListener {
            context.startActivity(data[position].intent)
        }
    }


    class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText = itemView.findViewById<TextView>(R.id.card_view_title_text)
        val snapshot = itemView.findViewById<ImageView>(R.id.card_view_snapshot_image)
    }
}