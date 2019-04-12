package com.example.coins

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.coin_list.view.*
import java.text.FieldPosition
import com.example.coins.Models.Coin



class CoinAdapter(val items: ArrayList<Coin>, val clickListener: (Coin)->Unit) : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.coin_list,parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],clickListener)

        //Glide.with(holder.itemView.context).load().into(holder.itemView.img_poke) en load pegar url de cada imagen, pero antes verificar si ese
        //url existe y es válido
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Coin, clickListener:  (Coin) -> Unit) = with(itemView){
            tv_name.text = item.nombre
            tv_country.text = item.country
            itemView.setOnClickListener{(clickListener(item))}
        }
    }
}