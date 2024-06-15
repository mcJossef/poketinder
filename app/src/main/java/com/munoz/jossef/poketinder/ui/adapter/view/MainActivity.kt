package com.munoz.jossef.poketinder.ui.adapter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.munoz.jossef.poketinder.R
import com.munoz.jossef.poketinder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)

        binding.navView.setupWithNavController(navController)
    }
}

