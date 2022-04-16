package com.example.mynotes

import android.content.Context
import android.graphics.Color

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class CustomSpinnerAdapter(var cont: Context, var list:ArrayList<String>): BaseAdapter() {

    private val inflater:LayoutInflater
    =cont.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view=inflater.inflate(R.layout.my_spinner_item,p2,false)

        val image=view.findViewById<ImageView>(R.id.spinner_item)
        image.setBackgroundColor(Color.parseColor(list.get(p0)))


        return view
    }

    override fun getCount(): Int {
       return list.size

    }

    override fun getItem(p0: Int): Any {
       return list[p0]
    }

    override fun getItemId(p0: Int): Long {
      return  p0.toLong()
    }



}