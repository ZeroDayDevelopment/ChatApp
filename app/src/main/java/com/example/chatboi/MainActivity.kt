package com.example.chatboi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.chatboi.adapter.FragmentPagerAdapter
import com.example.chatboi.fragments.CallsFragment
import com.example.chatboi.fragments.ChatsFragment
import com.example.chatboi.fragments.StatusFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

lateinit var auth:FirebaseAuth
lateinit var mGoogleSignInClient:GoogleSignInClient
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder()
            .requestIdToken(getString(R.string.app_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

        val fragmentPagerAdapter = FragmentPagerAdapter(supportFragmentManager,lifecycle)
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)


        fragmentPagerAdapter.addFragment(ChatsFragment(),"Chats")
        fragmentPagerAdapter.addFragment(StatusFragment(),"Status")
        fragmentPagerAdapter.addFragment(CallsFragment(),"Calls")
        viewPager.adapter = fragmentPagerAdapter
        TabLayoutMediator(tabLayout,viewPager){ tab, position ->
            tab.text = fragmentPagerAdapter.getPage(position)
        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_settings -> {
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show()
            }

            R.id.menu_logout -> {
                auth.signOut()
                mGoogleSignInClient.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }

            R.id.menu_groupChat -> {
                Toast.makeText(this,"Group Chat",Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}