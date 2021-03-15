package com.example.weatherapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapter.WeatherListAdapter
import com.example.weatherapp.databinding.FragmentWeatherListBinding
import com.example.weatherapp.ui.BaseFragment
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherListFragment : BaseFragment() {

    private lateinit var weatherListAdapter: WeatherListAdapter

    private lateinit var binding: FragmentWeatherListBinding

    override val viewModel by viewModel<WeatherListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherListAdapter = WeatherListAdapter()

        binding.apply {
            weatherRecyclerView.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = weatherListAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getWeatherList()
            }
        }

        viewModel.apply {
            isLoadingLiveData.observe(viewLifecycleOwner, { isLoading ->
                binding.swipeRefreshLayout.isRefreshing = isLoading
            })

            weatherListLiveData.observe(viewLifecycleOwner, {
                weatherListAdapter.clear()
                weatherListAdapter.add(it)
            })
        }

        weatherListAdapter.getPositionClickObservable().subscribe { cityId ->
            cityId?.let {
                findNavController().navigate(
                    WeatherListFragmentDirections.toDetailsFragment(
                        it
                    )
                )
            }
        }.addTo(compositeDisposable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }
}
