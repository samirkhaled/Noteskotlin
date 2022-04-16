package com.example.mynotes

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.GnssAntennaInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var mRef: DatabaseReference
    private lateinit var list:ArrayList<NoteModel>
      var adapter:NoteRVAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mRef= FirebaseDatabase.getInstance().getReference("Notes")
        list=ArrayList()

       binding.noteRv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        binding.mainScreenAddButton.setOnClickListener {
             val intent=Intent(this,Add_note::class.java)
            startActivity(intent)

        }

        //get notes fromDatabase
        getNotes()
        //search
        binding.searchButton.queryHint="Search here.."

        binding.searchButton.setOnSearchClickListener {
            binding.searchButton.setMaxWidth(Integer.MAX_VALUE);

        }

        binding.searchButton.setOnQueryTextListener(object :SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                 return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchInDB(p0)
                
                return false
            }

        })

    }

    private fun searchInDB(p0: String?) {
       mRef.addValueEventListener(object:ValueEventListener
       {
           override fun onDataChange(snapshot: DataSnapshot) {
             if (snapshot.exists())
             {
                 list.clear()
                 for (i in snapshot.children)
                 {
                     val model=i.getValue(NoteModel::class.java)
                     if(model?.title.equals(p0)||model?.title.toString().startsWith(p0!!)||
                         model?.title.toString().endsWith(p0!!)||model?.title.toString().contains(p0!!))
                     {
                         list.add(model!!)
                     }

                 }
                 if (list.size>0)
                 {
                     adapter?.notifyDataSetChanged()
                 }else
                 {
                     Toast.makeText(this@MainActivity,"not match anything",Toast.LENGTH_SHORT).show()
                     getNotes()

                 }

             }

           }

           override fun onCancelled(error: DatabaseError) {

           }

       })

    }


    fun getNotes(){
     list.clear()
     mRef.addValueEventListener(object :ValueEventListener
     {
         override fun onDataChange(snapshot: DataSnapshot) {
             for(i in snapshot.children)
             {
                 var model=i.getValue(NoteModel::class.java)!!
                 list.add(0,model)

             }
             adapter= NoteRVAdapter(this@MainActivity,list)
             binding.noteRv.adapter=adapter
             adapter?.notifyDataSetChanged()


         }

         override fun onCancelled(error: DatabaseError) {
             Toast.makeText(this@MainActivity,error.toString(),Toast.LENGTH_LONG).show()
         }

     })

 }

}