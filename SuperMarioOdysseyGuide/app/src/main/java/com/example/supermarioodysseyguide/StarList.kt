package com.example.supermarioodysseyguide

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.supermarioodysseyguide.adapter.StarAdapter
import com.example.supermarioodysseyguide.data.Datasource

class StarList : AppCompatActivity() {

    private lateinit var adapter: StarAdapter

    companion object {
        const val SEARCH_PREFIX = "https://www.google.com/search?q=super+mario+odyssey+"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star_list)

        //get kingdom from main activity
        val kingdom = intent?.extras?.getString("kingdom").toString()

        val stars = Datasource().loadStarList(this, kingdom)

        //change title of activity
        title = kingdom

        val prefs = getSharedPreferences(kingdom, MODE_PRIVATE)

        //set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_star)
        adapter = StarAdapter(this, prefs, kingdom, supportActionBar!!, stars)
        recyclerView.adapter = adapter

        //change properties of adapter based on saved values
        if (savedInstanceState != null) {
            adapter.isAll = savedInstanceState.getBoolean("filter")
        }
        adapter.selectedPos = prefs.getStringSet(kingdom, setOf<String>())!!.toMutableList()

        //set subtitle of action bar and enable up button
        supportActionBar?.subtitle = "${adapter.selectedPos.size} of ${stars.size} Power Moons Collected"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        recyclerView.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu_2, menu)

        val filterButton = menu?.findItem(R.id.action_filter)
        setIcon(filterButton)
        return true
    }

    //set up icon for filter action
    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) return
        menuItem.icon =
            if (adapter.isAll)
                ContextCompat.getDrawable(this, R.drawable.ic_star_empty)
            else ContextCompat.getDrawable(this, R.drawable.ic_star)
    }

    //when filter button is pressed, implement filter action
    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                adapter.isAll = !adapter.isAll
                setIcon(item)
                adapter.notifyDataSetChanged()

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //save filter action state
        outState.putBoolean("filter", adapter.isAll)
    }
}