package com.ahmedorabi.trackingapp.features.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmedorabi.trackingapp.R
import com.ahmedorabi.trackingapp.core.db.TripEntity
import com.ahmedorabi.trackingapp.databinding.FragmentDetailsBinding
import com.ahmedorabi.trackingapp.features.getTimeFormatted
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import timber.log.Timber


class DetailsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var trip: TripEntity? = null
    private lateinit var map: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trip = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable("trip_entity", TripEntity::class.java)
            } else {
                it.getParcelable("trip_entity")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Timber.e("Trip Entity %s", trip)

        trip?.let {
            binding.distanceTv.text = it.distance
            binding.stepsTv.text = it.steps
            binding.trackTimeTv.text = it.time.getTimeFormatted()
        }


        return binding.root

    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.isMyLocationEnabled = true
        map.uiSettings.isZoomControlsEnabled = true

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    trip?.currentLocation?.latitude ?: 0.0,
                    trip?.currentLocation?.longitude ?: 0.0
                ), 14f
            )
        )

        trip?.let {
            drawRoute(it.paths)

        }
    }


    private fun drawRoute(locations: List<LatLng>) {
        val polylineOptions = PolylineOptions()

        map.clear()

        val points = polylineOptions.points
        points.addAll(locations)

        map.addPolyline(polylineOptions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}