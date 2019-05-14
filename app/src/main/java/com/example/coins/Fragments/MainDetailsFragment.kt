package com.example.coins.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.coins.Models.Coin
import com.example.coins.R
import kotlinx.android.synthetic.main.fragment_main_details.view.*

class MainDetailsFragment : Fragment() {
    var coin = Coin("C",
        R.string.n_a_value.toString(),
        R.string.n_a_value.toString(),
        0,
        0,
        0,
        R.string.n_a_value.toString(),
        false,
        R.string.n_a_value.toString())

    companion object {
        fun newInstance(item: Coin): MainDetailsFragment {
            val newFragment = MainDetailsFragment()
            newFragment.coin = item
            return newFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_main_details, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        view.name_main_content_fragment.text = coin.nombre
        view.country_main_content_fragment.text = coin.country
        view.value_main_content_fragment.text = coin.value.toString()
        view.value_us_main_content_fragment.text = coin.value_us.toString()
        view.year_main_content_fragment.text = coin.year.toString()
        view.available_main_content_fragment.text = coin.available.toString()
        view.review_main_content_fragment.text = coin.review

        Glide.with(view.context)
            .load(coin.img)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)
    }
}