package com.example.asteroidradar.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentMainBinding
import com.example.asteroidradar.details.DetailsFragment
import com.example.asteroidradar.models.Asteroid
import com.squareup.picasso.Picasso

class MainFragment : Fragment() , OnItemClicked{

    private var _binding: FragmentMainBinding? = null

    private val mainViewModel: MainViewModel by viewModels() {
        MainViewModelFactory(requireContext())
    }
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MainAdapter(this)

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


    override fun onItemClicked(asteroid: Asteroid) {
        navigateToDetails(asteroid)
//        findNavController().navigate(R.id.detailsFragment)
//
//        var bundle = Bundle()
//        bundle.putParcelable("astroid" , asteroid)
//        Log.i("TAG", "onViewCreated: ${asteroid}")
//        val detailsfragment = DetailsFragment()
//        detailsfragment.arguments = bundle
//        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView, detailsfragment)?.commit()

    }
    private fun navigateToDetails(asteroid: Asteroid) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(asteroid)
        findNavController().navigate(action)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        getPictureOfTheDay()
        getAllAsteroid()
    }

    private fun getPictureOfTheDay() {
        mainViewModel.pictureResult.observe(viewLifecycleOwner) {
            if(it==null){
                _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
            }else{
                if (it.mediaType.equals("image")) {
                    _binding?.activityMainImageOfTheDay?.contentDescription=getString(R.string.nasa_picture_of_day_content_description_format,it.title)
                    Picasso.get()
                        .load(it.url)
                        .into(_binding?.activityMainImageOfTheDay)
                }else{
                    _binding?.activityMainImageOfTheDay?.contentDescription=("R.string.image_of_the_day")

                }
            }

        }
    }

    @SuppressLint("LogNotTimber")
    private fun getAllAsteroid() {
        _binding?.statusLoadingWheel?.visibility=View.VISIBLE


        mainViewModel.asteroidFilter.observe(viewLifecycleOwner) {

            Log.i("TAG", "getAllAsteroid: ${it} ")
            mainViewModel.asteroidResult.observe(viewLifecycleOwner) {

                Log.i("TAG", "getAllAsteroidddddddddddddddddddddddddddd: ${it.size} ")
                Log.e("TAG", " viewwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww$it ")
                _binding?.statusLoadingWheel?.visibility=View.GONE
                adapter.submitList(it)
            }
        }
    }

    private fun setUpRecyclerView() {
        _binding!!.asteroidRecycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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


