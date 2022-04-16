package com.example.mynotes

 class NoteModel
{
    var title:String?=null
    var note:String?=null
    var Color:String?=null
    var id:String?=null
    var date:String?=null

    constructor(title: String?, note: String?, Color: String?, id: String?,date:String?) {
        this.title = title
        this.note = note
        this.Color = Color
        this.id = id
        this.date = date
    }

    constructor()

}