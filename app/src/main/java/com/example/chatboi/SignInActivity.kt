package com.example.chatboi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    var googleRequestCode:Int = 99
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val signup_btn = findViewById<TextView>(R.id.tw_sign_up)
        val google_signin_btn = findViewById<TextView>(R.id.sign_in_google)
        val signin_btn = findViewById<Button>(R.id.sign_in)
        val progressDialog = ProgressDialog(this)

        auth = Firebase.auth

        google_signin_btn.setOnClickListener {
            signIn()
        }

        signup_btn.setOnClickListener {
            val action = Intent(this,SignUpActivity::class.java)
            startActivity(action)
            finish()
        }

        signin_btn.setOnClickListener {
            progressDialog.showDialog("Signing in...")
            val emailInput = findViewById<TextView>(R.id.signin_email).text.toString()
            val passwordInput = findViewById<TextView>(R.id.signin_password).text.toString()
            if (emailInput.isNotEmpty() && passwordInput.isNotEmpty()){

                auth.signInWithEmailAndPassword(emailInput,passwordInput).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        progressDialog.closeDialog()
                    }
                    else{
                        progressDialog.closeDialog()
                        Toast.makeText(baseContext,task.exception?.message?:"Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            else{
                Toast.makeText(baseContext,"Enter Credentials.", Toast.LENGTH_SHORT).show()
                progressDialog.closeDialog()
            }
        }
    }

    private fun signIn(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.app_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this,gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent,googleRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleRequestCode){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val result = task.getResult(ApiException::class.java)
                FireBaseAuthwithGoogle(result.idToken!!)
            }
            catch(e:ApiException){
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun FireBaseAuthwithGoogle(id:String){
        val credential = GoogleAuthProvider.getCredential(id,null)
        auth.signInWithCredential(credential).addOnCompleteListener {task ->
            if (task.isSuccessful){
                val user = auth.currentUser
                Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }


