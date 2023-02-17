package com.example.tifu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.model.Model
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.example.tifu.databinding.FragmentAllJokesBinding
import com.example.tifu.databinding.FragmentTifuBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Tifu : Fragment(){
    private var _binding: FragmentTifuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTifuBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.generateStory.setOnClickListener{
            var promptInput = binding.promptInput.text.toString()
            binding.responseSection.setText("Response : Loading... Please Wait :)" )
            GlobalScope.launch {
                        receiveOpenAiData(promptInput)
            }
        }
        return binding.root
    }

    suspend fun receiveOpenAiData(promptInput: String) {
        val openAI = OpenAI(BuildConfig.API_KEY)
        val completionRequest = CompletionRequest(
            model = ModelId("davinci:ft-personal-2023-02-11-13-36-59"),
            prompt = promptInput,
            maxTokens = 300,
            echo = true,
        )
        val completion: TextCompletion = openAI.completion(completionRequest)
        val resultData = completion.choices[0].text
        Log.d("plswork", completion.toString())

        val formattedText = formatResponse(resultData)

        activity?.runOnUiThread(Runnable {
            // Stuff that updates the UI
//            binding.responseSection.setText("Response : " + completion.choices[0].text)
            binding.responseSection.setText("Response : " + formattedText)

        })
    }

    fun formatResponse(response: String): String {
        var trimmedString = response.replace("\n", " ").replace("END", "").replace("\t", "").replace("    ", "").trim()
        return trimmedString
    }

}