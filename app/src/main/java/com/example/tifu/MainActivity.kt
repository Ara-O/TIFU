package com.example.tifu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
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
//        GlobalScope.launch {
//            receiveOpenAiData()
//        }

    }

    private fun replaceFragment(Fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentContainerView, Fragment)
            commit()
        }
    }
}