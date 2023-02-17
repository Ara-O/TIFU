package com.example.tifu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tifu.databinding.FragmentAllJokesBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Jokes {
    lateinit var dadJokes: HashMap<String, String>
    constructor(){}
    constructor(
        dadJokes:  HashMap<String, String>
    ){
        this.dadJokes = dadJokes
    }
}
class AllJokes : Fragment() {
    private var _binding: FragmentAllJokesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllJokesBinding.inflate(inflater, container, false)
        val view = binding.root


        val allJokesReference: DatabaseReference = Firebase.database.reference
        val jokesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val jokes = dataSnapshot.getValue<Jokes>()

                jokes?.dadJokes?.forEach {(key, joke)-> println("joke $key - $joke")
                    binding.jokesList.append("- $joke\n\n")
                }
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("all jokes", "loadPost:onCancelled", databaseError.toException())
            }
        }
        allJokesReference.addValueEventListener(jokesListener)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}