package com.example.mynotes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import com.example.mynotes.databinding.ActivityAddEditNoteBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList

class Edit_Note : AppCompatActivity() {
    private lateinit var binding:ActivityAddEditNoteBinding
    private lateinit var mRef:DatabaseReference
    var id:String?=null
   var adapter:CustomSpinnerAdapter?=null
    private lateinit var arry:ArrayList<String>
    private   var color:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id=intent.extras?.getString("id")
        mRef=FirebaseDatabase.getInstance().getReference("Notes")
        //colors
        arry= ArrayList()
        arry = ArrayList<String>()
        arry.add("#FFAB91")
        arry.add("#FFAB91")
        arry.add("#E6EE9B")
        arry.add("#80DEEA")
        arry.add("#CF93D9")
        arry.add("#F48FB1")
        arry.add("#F48FB1")
        arry.add("#80CBC4")
        //end colors
        //spinner
          adapter=CustomSpinnerAdapter(this,arry)
        binding.colorSpinner.adapter=adapter
        //
        getFromDB(id.toString())
        binding.editNoteEdit.setOnClickListener {
            updateNote()
            var inputManager: InputMethodManager =
                this@Edit_Note.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.
            hideSoftInputFromWindow(this@Edit_Note.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            binding.editNoteNote.isCursorVisible = false
            binding.editNoteTitle.isCursorVisible = false
        }
        binding.colorSpinner.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                color=getItemId(p2)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        })

        binding.editNoteBack.setOnClickListener {
           startActivity(Intent(this@Edit_Note,MainActivity::class.java))



        }

    }

    private fun getItemId(p2: Int):String{

        return arry.get(p2)
    }


    fun getFromDB(id:String)
    {
       mRef.child(id).addValueEventListener(object:ValueEventListener
       {
           override fun onDataChange(snapshot: DataSnapshot) {
               val model=snapshot.getValue(NoteModel::class.java)
               binding.editNoteDate.text=model?.date
               binding.editNoteNote.setText(model?.note)
               binding.editNoteTitle.setText(model?.title)
               binding.colorSpinner.setSelection(arry.indexOf(model?.Color.toString()))
           }
           override fun onCancelled(error: DatabaseError) {
               Toast.
               makeText(this@Edit_Note,
                   "Check your Internet Connection!",
                   Toast.LENGTH_LONG)
                   .show()
           }

       })

   }

    fun getCurrentDate():String
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
    private fun updateNote() {
        val title=binding.editNoteTitle.text.toString()
        val note= binding.editNoteNote.text.toString()
        if(checkIfFieldsNotNull(title,note))

        {
            var model=NoteModel(title,note,color,id,getCurrentDate())

            mRef.child(id.toString()).setValue(model).addOnSuccessListener {
                Toast.makeText(this@Edit_Note,"Saved",Toast.LENGTH_LONG).show()

            }.addOnFailureListener {
                Toast.makeText(this@Edit_Note,"Try later",Toast.LENGTH_LONG).show()

            }

        }else
        {
            Toast.makeText(this@Edit_Note,"must all fields not empty",Toast.LENGTH_LONG).show()


        }
    }
}