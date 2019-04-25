package com.example.coins

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.coins.Models.Coin

class CoinViewer : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_coin)

        val mIntent = intent

        if(mIntent!=null){

            val receiver: Coin = intent?.extras?.getParcelable("COIN") ?:Coin(R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), 0,0,0, R.string.n_a_value.toString(), false,R.string.n_a_value.toString())

        }


    }

}