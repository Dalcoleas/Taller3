package com.example.coins.Activities

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.coins.AppConstants
import com.example.coins.Fragments.MainDetailsFragment
import com.example.coins.Models.Coin
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.json.JSONObject
import com.example.coins.Fragments.MainListFragment
import com.example.coins.Network.NetworkUtils
import com.example.coins.R
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainListFragment.ListenerTools  {


    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var coinList = ArrayList<Coin>()

    private lateinit var mainFragment : MainListFragment
    private lateinit var mainContentFragment : MainDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_saveinstance_key,coinList)
        super.onSaveInstanceState(outState)
    }

    override fun managePortraitItemClick(item: Coin) {
        val coinBundle = Bundle()
        coinBundle.putParcelable("COIN",item)
        startActivity(Intent(this, CoinViewer::class.java).putExtras(coinBundle))
    }

    override fun manageLandscapeItemClick(item: Coin) {
        mainContentFragment = MainDetailsFragment.newInstance(item)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    fun initMainFragment(){

        mainFragment = MainListFragment.newInstance(coinList)


        val resource = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment

        else{
            mainContentFragment = MainDetailsFragment.newInstance(Coin("Cualquier estupidez",
                R.string.n_a_value.toString(),
                R.string.n_a_value.toString(),
                0,
                0,
                0,
                R.string.n_a_value.toString(),
                false,
                R.string.n_a_value.toString()))

            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

            R.id.land_main_fragment
        }

        changeFragment(resource,mainFragment)

        FetchCoinTask().execute("")

    }

    fun addCoinToList(coin: Coin){
        coinList.add(coin)
        mainFragment.updateCoinAdapter(coinList)
        Log.d("Number", coinList.size.toString())
    }


    private fun changeFragment(id: Int, frag:Fragment){ supportFragmentManager.beginTransaction().replace(id,frag).commit()}


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
            R.id.nav_all -> {
                // Handle the camera action
            }
            R.id.nav_el_salvador -> {

            }
            R.id.nav_guatemala -> {

            }
            R.id.nav_honduras -> {

            }
            R.id.nav_costa_rica -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private inner class FetchCoinTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("",ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(coinInfo: String) {
            Log.d("msg", coinInfo)

            val coins = if (!coinInfo.isEmpty()){
                val root = JSONObject(coinInfo)
                val results = root.getJSONArray("buscadores")

                MutableList(4){i->
                    val result = JSONObject(results[i].toString())

                    Coin(result.getString("_id"),
                        result.getString("nombre"),
                        result.getString("country"),
                        result.getInt("value"),
                        result.getInt("value_us"),
                        2019,
                        result.getString("review"),
                        result.getBoolean("available"),
                        result.getString("img"))
                }

            } else{
                MutableList(4){
                    Coin("Cualquier estupidez",
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        0,
                        0,
                        0,
                        R.string.n_a_value.toString(),
                        false,
                        R.string.n_a_value.toString())
                }
            }

            for(coin in coins){
                addCoinToList(coin)
            }
        }
    }


}
