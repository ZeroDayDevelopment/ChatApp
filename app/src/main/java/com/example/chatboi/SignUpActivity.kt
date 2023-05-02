package com.example.chatboi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.chatboi.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var auth:FirebaseAuth
    lateinit var database:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(getLayoutInflater())
        val view:View = binding.root
        setContentView(view)

        auth = Firebase.auth
        database = Firebase.database
        val progressdialog = ProgressDialog(this)
        val signin_btn = findViewById<TextView>(R.id.tw_sign_up)
        val signup_btn = findViewById<TextView>(R.id.sign_up)

        if (auth.currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        signup_btn.setOnClickListener {

            val username = findViewById<TextView>(R.id.signup_Username).text.toString()
            val userEmail = findViewById<TextView>(R.id.signup_Email).text.toString()
            val userPassword = findViewById<TextView>(R.id.signup_Password).text.toString()
            if (username.isNotEmpty() && userEmail.isNotEmpty() && userPassword.isNotEmpty()){
                progressdialog.showDialog("Signing Up...")
                auth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful){

                            val id = task.result.user!!.uid
                            val user = Users(username,userEmail,userPassword)
                            database.getReference().child("Users").child(id).setValue(user)
                            progressdialog.closeDialog()
                            Toast.makeText(baseContext,"Authentication success.", Toast.LENGTH_SHORT).show()

                        }

                        else{
                            progressdialog.closeDialog()
                            Toast.makeText(baseContext,"Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                progressdialog.closeDialog()
                Toast.makeText(baseContext,"Enter Credentials.", Toast.LENGTH_SHORT).show()
            }
        }

        signin_btn.setOnClickListener {
            val action = Intent(this,SignInActivity::class.java)
            startActivity(action)
            finish()
        }


    }
}
