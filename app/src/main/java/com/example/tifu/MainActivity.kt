package com.example.tifu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.tifu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tifu.setOnClickListener{
            replaceFragment(Tifu());
        }
        binding.dadJokes.setOnClickListener{
            replaceFragment(DadJokes());
        }

        binding.savedJokes.setOnClickListener{
            replaceFragment(AllJokes());
        }

    }

    private fun replaceFragment(Fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentContainerView, Fragment)
            commit()
        }
    }
}