package com.example.mynotes

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NoteRVAdapter(var cont:Context,var list:ArrayList<NoteModel>):
    RecyclerView.Adapter<NoteRVAdapter.Holder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val view=LayoutInflater.from(cont).inflate(R.layout.note_rv_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model=list.get(position)
        holder.card.setBackgroundColor(Color.parseColor(model.Color))
        holder.date.text=model.date
        holder.title.text=model.title
        holder.card.setOnClickListener {
            var intent=Intent(cont,Edit_Note::class.java)
            intent.putExtra("id",model.id)
            cont.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder( itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val card=itemView.findViewById<androidx.cardview.widget.CardView>(R.id.note_rv_cardView)
        val title=itemView.findViewById<TextView>(R.id.note_rv_ite_title)
        val date=itemView.findViewById<TextView>(R.id.note_rv_ite_date)
    }
}