package com.example.supermarioodysseyguide.data

import android.content.Context
import com.example.supermarioodysseyguide.R
import com.example.supermarioodysseyguide.model.Kingdom
import com.example.supermarioodysseyguide.model.Star
import java.util.*

class Datasource {
    //create list of kingdoms in Super Mario Odyssey
    fun loadKingdoms(): List<Kingdom> {
        return listOf(
            Kingdom(R.string.kingdom1, R.drawable.kingdom1),
            Kingdom(R.string.kingdom2, R.drawable.kingdom2),
            Kingdom(R.string.kingdom3, R.drawable.kingdom3),
            Kingdom(R.string.kingdom4, R.drawable.kingdom4),
            Kingdom(R.string.kingdom5, R.drawable.kingdom5),
            Kingdom(R.string.kingdom6, R.drawable.kingdom6),
            Kingdom(R.string.kingdom7, R.drawable.kingdom7),
            Kingdom(R.string.kingdom8, R.drawable.kingdom8),
            Kingdom(R.string.kingdom9, R.drawable.kingdom9),
            Kingdom(R.string.kingdom10, R.drawable.kingdom10),
            Kingdom(R.string.kingdom11, R.drawable.kingdom11),
            Kingdom(R.string.kingdom12, R.drawable.kingdom12),
            Kingdom(R.string.kingdom13, R.drawable.kingdom13),
            Kingdom(R.string.kingdom14, R.drawable.kingdom14),
            Kingdom(R.string.kingdom15, R.drawable.kingdom15),
            Kingdom(R.string.kingdom16, R.drawable.kingdom16),
            Kingdom(R.string.kingdom17, R.drawable.kingdom17)
        )
    }

    //create list of moons based on the kingdom
    fun loadStarList(context: Context, kingdom: String): List<Star> {
        //beginning part of file is based on the name of the kingdom
        val fileStarter = when (kingdom) {
            "Cap Kingdom" -> "kingdom1"
            "Cascade Kingdom" -> "kingdom2"
            "Sand Kingdom" -> "kingdom3"
            "Lake Kingdom" -> "kingdom4"
            "Wooded Kingdom" -> "kingdom5"
            "Cloud Kingdom" -> "kingdom6"
            "Lost Kingdom" -> "kingdom7"
            "Metro Kingdom" -> "kingdom8"
            "Snow Kingdom" -> "kingdom9"
            "Seaside Kingdom" -> "kingdom10"
            "Luncheon Kingdom" -> "kingdom11"
            "Ruined Kingdom" -> "kingdom12"
            "Bowser\'s Kingdom" -> "kingdom13"
            "Moon Kingdom" -> "kingdom14"
            "Mushroom Kingdom" -> "kingdom15"
            "Dark Side" -> "kingdom16"
            else -> "kingdom17"
        }

        val file = fileStarter + "_star_list.txt"
        var starList = mutableListOf<Star>()
        var iteration = 1

        //read from file, create a Star per line and add to list of stars
        context.assets.open(file).bufferedReader().useLines { lines -> lines.forEach {starList.add(Star(
            "$iteration. $it"
        ))
        iteration += 1} }

        //convert mutable list to unmodifiable list
        starList = Collections.unmodifiableList(starList)

        return starList
    }
}