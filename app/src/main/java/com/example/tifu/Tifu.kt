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

    suspend fun receiveOpenAiData(promptInput: String){
        val openAI = OpenAI(BuildConfig.API_KEY)
        val models: List<Model> = openAI.models()
//        for (e in models){
//            Log.d("eee", e.toString())
//            val completionRequest = CompletionRequest(
//                model= ModelId("davinci:ft-personal-2023-02-11-13-36-59"),
//                prompt = promptInput,
//                maxTokens = 400,
//                echo = true,
//            )
//            val completion: TextCompletion = openAI.completion(completionRequest)
//            val resultData = completion.choices[0].text
//            Log.d("plswork", completion.toString())
        val resultDataTest = "TIFU by eating too many gummie bears and having to poop massively in the toilet.\n" +
                "    \n" +
                "    Not a FU. Happened two hours ago\n" +
                "    \n" +
                "    I was hungry and opened the bag of gummie bears I bought to have over the weekend. Having a sweet to eat makes you feel better about life and honestly going to bed with my stomach somewhat full is life changing. Eat now, sleep feeling like a pig later.\n" +
                "    \n" +
                "    Well guess what happened~\n" +
                "    \n" +
                "    I ate a handful of medium sized gummie bears and decided I needed to see the toilet urgently.\n" +
                "    \n" +
                "    Well evidently that bears were mostly neon gummie and other colours because I had to drop a big one (with some grunts, naturally) that engulfed the whole toilet. When i pulled the flush lever the mass stayed there, nose level.\n" +
                "    \n" +
                "    Tbh tho despite being exhausted like a hungover man, it felt amazing knowing I was lowering the amount of toilet paper used and cleaning the bowl without breaking a sweat. I felt like super woman and I felt a little warm inside.\n" +
                "    \n" +
                "    I went to bed with a smile on my face.\n" +
                "    \n" +
                "    TL;DR: Eated too many gummie bears and had to drop a big one to salute the toilet\n" +
                "    \n" +
                "    EDIT: Wow my first time getting above 100 upvotes in less than 24 hours  END EDIT\n" +
                "    \n" +
                "     END TLDR\n" +
                "    \n" +
                "     END END END END ENDED END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END END"

        val formattedText = formatResponse(resultDataTest)

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