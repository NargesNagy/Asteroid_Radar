package com.example.asteroidradar.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentDetailsBinding
import com.example.asteroidradar.models.Asteroid

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val args by navArgs<DetailsFragmentArgs>()

    //var arg = this.arguments
     //arg?.get("astroid")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_details, container, false)
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        _binding?.lifecycleOwner=this

        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("TAG", "onViewCreatedtttttttttttttttttttttttt: ${args.asteroidArgs}")

        if(args != null) {
            Log.i("TAG", "onViewCreatedttttttrrooooooooooooooooooo: ${args.asteroidArgs}")
            _binding!!.asteroid = args.asteroidArgs
//            if(args.asteroidArgs.isPotentiallyHazardous){
//                _binding!!.mainImvOfTheDay.setImageResource(R.drawable.asteroid_safe)
//                //_binding!!.mainImvOfTheDay.stat
//            }else{
//                _binding!!.mainImvOfTheDay.setImageResource(R.drawable.asteroid_hazardous)
//
//            }
        }

       // _binding.mainImvOfTheDay
        _binding?.helpButton?.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

    }

    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}