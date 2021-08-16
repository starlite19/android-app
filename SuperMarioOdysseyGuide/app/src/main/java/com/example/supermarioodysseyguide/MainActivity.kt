package com.example.supermarioodysseyguide

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.supermarioodysseyguide.adapter.KingdomAdapter
import com.example.supermarioodysseyguide.data.Datasource

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: KingdomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myDataset = Datasource().loadKingdoms()

        //set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = KingdomAdapter(this, myDataset)
        recyclerView.adapter = adapter

        recyclerView.setHasFixedSize(true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        val searchButton = menu?.findItem(R.id.action_search)

        //set up search function on app bar
        val searchView: SearchView = searchButton?.actionView as SearchView
        searchView.queryHint = "Search Kingdom Here"
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })
        return true
    }
}