package com.example.coins

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.example.coins.Models.Coin
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AssyncTaskHandleJson().execute()
    }

    inner class AssyncTaskHandleJson : AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            val info : String
            val url = "https://apimoviles.herokuapp.com/api/coin"
            val conn = URL(url).openConnection() as HttpURLConnection

            try{
                conn.connect()
                info = conn.inputStream.use { it.reader().use{reader-> reader.readText()} }
                Log.d("msg", "URL verificada con éxito!")
                Log.d("msg", info)
            } finally {
                conn.disconnect()
            }
            return info
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(result: String?){

        val main = JSONObject(result)

        val coin = main.getJSONArray("buscadores")

        var x = 0

        var id:String
        var nombre:String
        var pais:String
        var valor:Int
        var valorUs:Int
        var anio:Int?
        var resenia:String
        var disponible:Boolean
        var imagen:String

        val coins = ArrayList<Coin>()


        while(x < coin.length()-1) {
            val jsonObject = coin.getJSONObject(x)

            id = jsonObject.getString("_id")
            nombre = jsonObject.getString("nombre")
            pais = jsonObject.getString("country")
            valor = jsonObject.getInt("value")
            valorUs = jsonObject.getInt("value_us")
            anio = jsonObject.getInt("year")
            resenia = jsonObject.getString("review")
            disponible = jsonObject.getBoolean("available")
            imagen = jsonObject.getString("img")

            coins.add(Coin(
                    id,
                    nombre,
                    pais,
                    valor,
                    valorUs,
                    anio,
                    resenia,
                    disponible,
                    imagen
            ))
            x++
        }

        /*val coins:MutableList<Coin> = MutableList(4){
            i-> Coin(JSONObject(coin.getString(i)).getString("_id"),
            JSONObject(coin.getString(i)).getString("nombre"),
            JSONObject(coin.getString(i)).getString("country"),
            JSONObject(coin.getString(i)).getInt("value"),
            JSONObject(coin.getString(i)).getInt("value_us"),
            JSONObject(coin.getString(i)).getInt("year"),
            JSONObject(coin.getString(i)).getString("review"),
            JSONObject(coin.getString(i)).getBoolean("available"),
            JSONObject(coin.getString(i)).getString("img"))
        }*/

        viewManager = GridLayoutManager(this,2)
        viewAdapter = CoinAdapter(coins,{item: Coin->itemClicked(item)})

        rv_coins.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun itemClicked(item:Coin){

        Toast.makeText(this,"Clicked:  ${item.country}", Toast.LENGTH_LONG).show()
    }
}
