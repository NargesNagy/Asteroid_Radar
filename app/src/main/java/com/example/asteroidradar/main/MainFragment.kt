package com.example.asteroidradar.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding
import com.example.asteroidradar.models.Asteroid
import com.squareup.picasso.Picasso

class MainFragment : Fragment() , OnItemClicked{

    private var _binding: FragmentMainBinding? = null
    private val mainViewModel: MainViewModel by viewModels() {
        MainViewModelFactory(requireContext())

    }
    private lateinit var adapter: MainAdapter
    //var connectivity : ConnectivityManager? = null
    //var info :NetworkInfo? = null
    //var contextt = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MainAdapter(this)
     //   mainViewModel.getSavedAsteroid()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_main, container, false)
        _binding = FragmentMainBinding.inflate(layoutInflater)

        setHasOptionsMenu(true)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        getPictureOfTheDay()
        getAllAsteroid()
    }

    override fun onItemClicked(asteroid: Asteroid) {
        navigationToDetails(asteroid)
//        findNavController().navigate(R.id.detailsFragment)
//
//        var bundle = Bundle()
//        bundle.putParcelable("astroid" , asteroid)
//        Log.i("TAG", "onViewCreated: ${asteroid}")
//        val detailsfragment = DetailsFragment()
//        detailsfragment.arguments = bundle
//        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, detailsfragment)?.commit()

    }

    private fun navigationToDetails(asteroid: Asteroid) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(asteroid)
        findNavController().navigate(action)

    }



    private fun getPictureOfTheDay() {

        mainViewModel.pictureOfTheDay.observe(viewLifecycleOwner) {
            it.observe(viewLifecycleOwner){


                if(it==null){
                    _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
                }else{
                    Picasso.get()
                        .load(it.url)
                        .into(_binding?.activityMainImageOfTheDay)


                    if (it.mediaType.equals("image")) {
                        _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.nasa_picture_of_day_content_description_format,it.title)
                        Picasso.get()
                            .load(it.url)
                            .into(_binding?.activityMainImageOfTheDay)
                    }else{
                        _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.image_of_the_day)
                        // this is a static image it will show if the result is video
                        _binding?.activityMainImageOfTheDay?.setImageResource(R.drawable.apod)

                    }
                }

            }

        }
/*
        mainViewModel.pictureResult.observe(viewLifecycleOwner) {

                if(it==null){
                    _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
                }else{
                    Picasso.get()
                        .load(it.url)
                        .into(_binding?.activityMainImageOfTheDay)


                    if (it.mediaType.equals("image")) {
                        _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.nasa_picture_of_day_content_description_format,it.title)
                        Picasso.get()
                            .load(it.url)
                            .into(_binding?.activityMainImageOfTheDay)
                    }else{
                        _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.image_of_the_day)

                    }
                }



        }
        */

    }

    @SuppressLint("LogNotTimber")
    private fun getAllAsteroid() {

        _binding?.statusLoadingWheel?.visibility=View.VISIBLE

        mainViewModel.asteroidFilter.observe(viewLifecycleOwner) {


            Log.i("TAG", "getAllAsteroid: ${it} ")
            mainViewModel.asteroidResult.observe(viewLifecycleOwner) {
                //mainViewModel.getSavedAsteroid()

                Log.i("TAG", "getAllAsteroidddddddddddddddddddddddddddd: ${it.size} ")
                if(it.size == 0){
                    Log.e("TAG", " viewwwwwwwwwwwwwwww$it ")
                    _binding?.statusLoadingWheel?.visibility=View.GONE
                }else{
                    Log.e("TAG", " viewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww$it ")
                    _binding?.statusLoadingWheel?.visibility=View.GONE
                    adapter.submitList(it)

                }


                  }
        }

    }

    private fun setUpRecyclerView() {
        _binding!!.asteroidRecycler.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menue, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.week_asteroids -> mainViewModel.getAsteroidONextWeek()
            R.id.today_asteroids -> mainViewModel.getAsteroidOfToday()
            R.id.saved_asteroids -> mainViewModel.getSavedAsteroid()
        }
        return true
    }
}




