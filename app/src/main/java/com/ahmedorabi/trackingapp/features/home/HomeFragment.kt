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
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        requestAllRequiredPermission()

        viewModel.initProviders(activity as AppCompatActivity)

        binding!!.startBtn.setOnClickListener {
            startTracking()
        }

        binding!!.endBtn.setOnClickListener {
            stopTracking()
        }

        viewModel.ui.observe(viewLifecycleOwner) {
            updateUi(it)

        }

        return binding!!.root

    }


    private fun startTracking() {

        binding!!.stepsTv.text = ""
        binding!!.distanceTv.text = ""
        binding!!.trackTimeTv.base = SystemClock.elapsedRealtime()
        binding!!.trackTimeTv.start()

        viewModel.startTracking()
    }

    private fun stopTracking() {

        viewModel.stopTracking()

        binding!!.trackTimeTv.stop()

        Navigation.findNavController(binding!!.root)
            .navigate(R.id.action_homeFragment_to_historyFragment)

    }


    private fun updateUi(ui: TripUI) {

        binding!!.distanceTv.text = ui.formattedDistance
        binding!!.stepsTv.text = ui.formattedPace
        Timber.e("current location %s", ui.currentLocation)

        if (ui.formattedPace.isNotEmpty()) {
            binding!!.circularProgressBar.progress = ui.formattedPace.toFloat()

        }


        viewModel.addTrip(
            steps = ui.formattedPace,
            distance = ui.formattedDistance,
            currentLocation = ui.currentLocation ?: LatLng(0.0, 0.0),
            paths = ui.userPath,
            time = SystemClock.elapsedRealtime() - binding!!.trackTimeTv.base

        )

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


