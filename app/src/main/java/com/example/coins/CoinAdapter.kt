package com.example.coins

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
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

        var image = items[position].img
        Log.d("msg",image)

        val len:Int = image.length

        if(len < 20){
            Glide.with(holder.itemView.context).load(R.drawable.ic_launcher_foreground).into(holder.itemView.img_coin)
        } else{
            Glide.with(holder.itemView.context).load(image).into(holder.itemView.img_coin)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Coin, clickListener:  (Coin) -> Unit) = with(itemView){
            tv_name.text = item.nombre
            tv_country.text = item.country
            itemView.setOnClickListener{(clickListener(item))}
        }
    }
}