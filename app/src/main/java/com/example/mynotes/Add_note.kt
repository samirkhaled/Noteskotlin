package com.example.mynotes

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.mynotes.databinding.ActivityAddNoteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Add_note : AppCompatActivity() {

    private lateinit var binding:ActivityAddNoteBinding
    private lateinit var mRef: DatabaseReference
    var color:String?=null
    lateinit var   arry:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mRef=FirebaseDatabase.getInstance().getReference("Notes")
        arry = ArrayList<String>()
         arry.add("#FFAB91")
         arry.add("#FFAB91")
         arry.add("#E6EE9B")
         arry.add("#80DEEA")
         arry.add("#CF93D9")
         arry.add("#F48FB1")
         arry.add("#F48FB1")
         arry.add("#80CBC4")

       val adapter=CustomSpinnerAdapter(this,arry)
         binding.colorSpinner.adapter=adapter


        binding.colorSpinner.setOnItemSelectedListener(object:AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                color=getItemId(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })




    }

    private fun getItemId(p2: Int):String{

      return arry.get(p2)
    }

    private fun addToDataBase() {
        val id=mRef.push().key.toString()
        val title=binding.addFragTitle.text.toString()
        val note= binding.addFragNote.text.toString()

        if(checkIfFieldsNotNull(title,note))

        {
          var model=NoteModel(title,note,color,id,getDateNow())

           mRef.child(id.toString()).setValue(model).addOnSuccessListener {
               Toast.makeText(this@Add_note,"Saved",Toast.LENGTH_LONG).show()
               startActivity(Intent(this@Add_note,MainActivity::class.java))
               finish()

           }.addOnFailureListener {
               Toast.makeText(this@Add_note,"Try later",Toast.LENGTH_LONG).show()

           }

        }else
        {
            Toast.makeText(this@Add_note,"must all fields not empty",Toast.LENGTH_LONG).show()


        }
    }

    override fun onStart() {
        super.onStart()
        //button Back to mainActivity
        binding.addFragBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        //end
        //button save to Data bse
        binding.addFragSave.setOnClickListener {

            addToDataBase()

        }
        //end

    }

   fun getDateNow():String
   {
       val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
       val currentDate = sdf.format(Date())

     return currentDate.toString()
   }

   fun checkIfFieldsNotNull(t:String,n:String):Boolean
   {
       var boll=false
       if(n.isNotEmpty()&&t.isNotEmpty())
       {
           boll=true
       }
       return boll
   }

    override fun onBackPressed() {
        super.onBackPressed()
//        startActivity(Intent(this,MainActivity::class.java))
//        finish()
    }


}