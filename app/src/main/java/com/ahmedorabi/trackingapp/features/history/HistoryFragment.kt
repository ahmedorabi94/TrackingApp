package com.ahmedorabi.trackingapp.features.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModels()
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val adapter = TripAdapter { trip ->

        val bundle = Bundle()
        bundle.putParcelable("trip_entity", trip)

        Navigation.findNavController(binding.root)
            .navigate(R.id.action_historyFragment_to_detailsFragment, bundle)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        binding.recyclerViewMain.adapter = adapter


        viewModel.allTrips.observe(viewLifecycleOwner) {
            it?.let {
                Timber.e("All Trips %s", it)
                adapter.submitList(it)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}