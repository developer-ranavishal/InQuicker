 package com.example.inquicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.inquicker.databinding.ActivityNewsBinding


 class NewsActivity : AppCompatActivity(){
     private lateinit var binding:ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController=findNavController(R.id.nav_host_fragment_container)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}