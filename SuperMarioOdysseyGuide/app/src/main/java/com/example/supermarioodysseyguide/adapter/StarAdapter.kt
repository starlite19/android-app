package com.example.supermarioodysseyguide.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.example.supermarioodysseyguide.R
import com.example.supermarioodysseyguide.StarList
import com.example.supermarioodysseyguide.model.Star
import com.google.android.material.card.MaterialCardView

class StarAdapter(private val context: Context, private val prefs: SharedPreferences, private val kingdom: String,
                  private val actionBar: ActionBar,
                  private val starSet: List<Star>) : RecyclerView.Adapter<StarAdapter.StarViewHolder>() {

    //list of positions in dataset that have been selected
    lateinit var selectedPos: MutableList<String>

    //Boolean value that represents if the user wants of filter the list or not
    var isAll = true

    class StarViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val checkedTextView: CheckedTextView = view.findViewById(R.id.star_title)
        val cardView: MaterialCardView = view.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_star, parent, false)

        return StarViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        val item = starSet[position]
        //set name of moon
        holder.checkedTextView.text = item.starString

        //if the corresponding view has been selected, mark as checked
        if (selectedPos.contains("$position")) {
            holder.checkedTextView.setCheckMarkDrawable(R.drawable.moon)
            holder.checkedTextView.isChecked = true
            if(isAll){
                //if we do not want to filter list, the selected moons should be visible
                holder.cardView.visibility = View.VISIBLE
            } else {
                //otherwise, the selected moons should be gone
                holder.cardView.visibility = View.GONE
            }
        } else {
            //unselected moons should show that they are unchecked
            holder.checkedTextView.checkMarkDrawable = null
            holder.checkedTextView.isChecked = false
        }

        //if we click the view, we will select the moon
        holder.checkedTextView.setOnClickListener{
            if(holder.checkedTextView.isChecked){
                //uncheck moon if it is checked
                holder.checkedTextView.checkMarkDrawable = null
                //remove position from list of selected positions
                selectedPos.remove("$position")
            } else {
                //selected moon of it is unchecked
                holder.checkedTextView.setCheckMarkDrawable(R.drawable.moon)
                //add position to list of selected positions
                selectedPos.add("$position")
                if(!isAll){
                    //if we want to filter the moons, make the now selected moon to be gone
                    holder.cardView.visibility = View.GONE
                }
            }
            //set the appropriate check value
            holder.checkedTextView.isChecked = !holder.checkedTextView.isChecked

            //change the number of selected moons in the action bar
            actionBar.subtitle = "${selectedPos.size} of ${starSet.size} Power Moons Collected"

            //save the positions of the selected moons as a set to shared preferences
            val editor = prefs.edit()

            editor.putStringSet(kingdom, selectedPos.toSet())
            editor.apply()

        }

        //on long click, search google for the moon name
        holder.checkedTextView.setOnLongClickListener {
            val queryList = item.starString.split(" ")
            //take all elements except for the first and join them
            val queryName = queryList.slice(IntRange(1, queryList.size-1)).joinToString("+")

            val queryUrl: Uri = Uri.parse("${StarList.SEARCH_PREFIX}${queryName}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context.startActivity(intent)
            true
        }
    }

    override fun getItemCount() = starSet.size

}