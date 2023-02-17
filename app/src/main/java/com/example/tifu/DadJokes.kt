package com.example.tifu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.tifu.databinding.FragmentAllJokesBinding
import com.example.tifu.databinding.FragmentDadJokesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DadJokes : Fragment() {
    private var _binding: FragmentDadJokesBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDadJokesBinding.inflate(inflater, container, false)
// ...
        database = Firebase.database.reference
        binding.generatePunchline.setOnClickListener{
            var promptInput = binding.promptInput.text.toString()
            if(binding.promptInput.length() == 0){
                binding.responseSection.setText("Response : Please Input a valid prompt" )
            }else {
                binding.responseSection.setText("Response : Loading... Please Wait :)" )
                GlobalScope.launch {
                    receiveOpenAiData(promptInput)
                }
            }
        }
        return binding.root
    }

    suspend fun receiveOpenAiData(promptInput: String){
        val openAI = OpenAI(BuildConfig.API_KEY)
        val models: List<Model> = openAI.models()
//        for (e in models){
//            Log.d("eee", e.toString())
//        }
            val completionRequest = CompletionRequest(
                model= ModelId("davinci:ft-personal-2023-02-16-15-06-34"),
                prompt = promptInput,
                maxTokens = 65,
                echo = true,
            )
            val completion: TextCompletion = openAI.completion(completionRequest)
            val resultData = completion.choices[0].text

            Log.d("plswork", completion.toString())
            val formattedText = formatResponse(resultData)

//            val myRef = database.getReference("message")
//
//            myRef.setValue("Hello, World!")

        activity?.runOnUiThread(Runnable {
            // Stuff that updates the UI
//           binding.responseSection.setText("Response : " + completion.choices[0].text)
            binding.responseSection.setText("Response : " + formattedText)
            binding.saveJoke.visibility = View.VISIBLE
            binding.saveJoke.setOnClickListener {
                Firebase.database.getReference("dadJokes").push().setValue(formattedText);
            }
        })
    }
    fun formatResponse(response: String): String {
        var trimmedString = response.replace("\n", " ").replace("END", "").replace("\t", "").replace("    ", "").trim()
        return trimmedString
    }


}