package com.ahmedorabi.trackingapp.features.home

import android.Manifest
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.databinding.FragmentHomeBinding
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        requestAllRequiredPermission()

        viewModel.initProviders(activity as AppCompatActivity)

        binding.startBtn.setOnClickListener {
            startTracking()
        }

        binding.endBtn.setOnClickListener {
            stopTracking()
        }

        viewModel.tripState.observe(viewLifecycleOwner) {
            updateUi(it)

        }

        return binding.root

    }


    private fun startTracking() {

        binding.mainConstraint.visibility = View.VISIBLE
        binding.stepsTv.text = ""
        binding.distanceTv.text = ""
        binding.trackTimeTv.base = SystemClock.elapsedRealtime()
        binding.trackTimeTv.start()

        viewModel.startTracking()
    }

    private fun stopTracking() {

        val ui = viewModel.tripState.value

        if (ui != TripUI.EMPTY) {

            ui?.let {
                viewModel.addTrip(
                    steps = ui.steps,
                    distance = ui.distance,
                    currentLocation = ui.currentLocation ?: LatLng(0.0, 0.0),
                    paths = ui.userPath,
                    time = SystemClock.elapsedRealtime() - binding.trackTimeTv.base

                )

                binding.mainConstraint.visibility = View.GONE

                viewModel.stopTracking()

                binding.trackTimeTv.stop()


                viewModel.tripState.value = TripUI.EMPTY
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_homeFragment_to_historyFragment)

            }

        }


    }


    private fun updateUi(ui: TripUI) {

        binding.distanceTv.text = ui.distance
        binding.stepsTv.text = ui.steps
        if (ui.steps.isNotEmpty()) {
            binding.circularProgressBar.progress = ui.steps.toFloat()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            Timber.e("${it.key} = ${it.value}")
        }
    }

    private fun requestAllRequiredPermission() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        )
    }

}


