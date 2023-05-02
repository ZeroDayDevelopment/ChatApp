package com.example.chatboi

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView

class ProgressDialog {
    val context:Context
    lateinit var dialog:Dialog

    constructor(context: Context){
        this.context = context
    }

    fun showDialog(message:String){
        dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogMessage  = dialog.findViewById<TextView>(R.id.DialogMessage)
        dialogMessage.setText(message)
        dialog.create()
        dialog.show()
    }

    fun closeDialog(){
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}