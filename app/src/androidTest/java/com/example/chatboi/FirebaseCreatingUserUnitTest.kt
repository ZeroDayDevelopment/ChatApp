package com.example.chatboi

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FirebaseCreatingUserUnitTest {
    @Test
    fun createUserwithEmailandPassword(){
        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val testUsername = "TestUser"
        val testEmail = "test@gmail.com"
        val testPasswd = "testtest123"
        var latch = CountDownLatch(1)

        auth.createUserWithEmailAndPassword(testEmail,testPasswd).addOnCompleteListener {task ->
            if (task.isSuccessful){
                val id = task.result.user!!.uid
                val user = Users(testUsername,testEmail,testPasswd)

                database.getReference("Users").child(id?:"").setValue(user).addOnCompleteListener {databaseTask ->
                    if (databaseTask.isSuccessful){
                        latch.countDown()
                    }
                    else{
                        fail("Failed to write database: ${databaseTask.exception}")
                    }
                }
            }

            else{
                fail("Failed to create user: ${task.exception}")
            }

            if (!latch.await(10,TimeUnit.SECONDS)){
                fail("Time is out")
            }
        }

            val databaseRef = database.getReference("Users")

            databaseRef.child(auth.currentUser!!.uid).get()
            .addOnCompleteListener {databaseRef ->
                if (databaseRef.isSuccessful){
                    val user = databaseRef.result.getValue(Users::class.java)
                    assertEquals(testUsername,user?.getusername())
                    assertEquals(testEmail,user?.getmail())
                    assertEquals(testPasswd,user?.getpassword())
                }
                else{
                    fail("Failed to read data from database: ${databaseRef.exception}")
                }
            }
    }
}