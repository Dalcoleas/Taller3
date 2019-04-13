package com.example.coins

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.coins.Models.Coin
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        AssyncTaskHandleJson().execute()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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

        var id:String?
        var nombre:String?
        var pais:String?
        var valor:Int?
        var valorUs:Int?
        var anio:Int?
        var resenia:String?
        var disponible:Boolean?
        var imagen:String

        val coins = ArrayList<Coin>()


        while(x < coin.length()) {
            val jsonObject = coin.getJSONObject(x)

            if(jsonObject.isNull("year")){
                jsonObject.put("year",0)
            }

            id = jsonObject?.getString("_id")
            nombre = jsonObject?.getString("nombre")
            pais = jsonObject?.getString("country")
            valor = jsonObject?.getInt("value")
            valorUs = jsonObject?.getInt("value_us")
            anio = jsonObject?.getInt("year")
            resenia = jsonObject?.getString("review")
            disponible = jsonObject?.getBoolean("available")
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
