package com.example.supermarioodysseyguide.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.supermarioodysseyguide.R
import com.example.supermarioodysseyguide.StarList
import com.example.supermarioodysseyguide.model.Kingdom

class KingdomAdapter(private val context: Context,
                     private val dataset: List<Kingdom>): RecyclerView.Adapter<KingdomAdapter.KingdomViewHolder>(),
                        Filterable {

    //mutable list of kingdoms to display on screen
    var kingdomFilterList = dataset.toMutableList()

    class KingdomViewHolder(private val _view: View) : RecyclerView.ViewHolder(_view){
        val view get() = _view
        val textView: TextView = _view.findViewById(R.id.item_title)
        val imageView: ImageView = _view.findViewById(R.id.item_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KingdomViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return KingdomViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: KingdomViewHolder, position: Int) {
        val item = kingdomFilterList[position]
        //set card values
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)

        //on click, go to activity that shows the corresponding kingdom's list of moons
        holder.itemView.setOnClickListener{
            val context = holder.view.context
            val intent = Intent(context, StarList::class.java)
            //send name of kingdom to next activity
            intent.putExtra("kingdom", context.resources.getString(item.stringResourceId))
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = kingdomFilterList.size

    //use filter to decide which kingdoms to display while using search view
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchString = constraint?.toString() ?: ""
                kingdomFilterList = if (searchString.isEmpty())
                    dataset as MutableList<Kingdom>
                else {
                    val filterList = mutableListOf<Kingdom>()
                    //filter based on name of kingdom
                    dataset.filter {
                        context.resources.getString(it.stringResourceId).contains(searchString, true)
                    }
                        .forEach { filterList.add(it) }
                    filterList
                }
                return FilterResults().apply {values = kingdomFilterList}
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                kingdomFilterList = if (results?.values == null)
                    mutableListOf()
                else
                    results.values as MutableList<Kingdom>
                notifyDataSetChanged()
            }

        }
    }

}