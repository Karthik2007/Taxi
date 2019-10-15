package com.karthik.taxi.ui.taximap

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.karthik.taxi.R
import com.karthik.taxi.databinding.ActivityTaxiMapBinding
import com.karthik.taxi.model.FleetType
import com.karthik.taxi.model.Poi
import com.karthik.taxi.ui.taxilist.TaxiListFragment
import com.karthik.taxi.ui.taxilist.TaxiViewModel
import com.karthik.taxi.utils.AppConstants.Companion.AREA_ZOOM_LEVEL
import com.karthik.taxi.utils.AppConstants.Companion.CITY_ZOOM_LEVEL
import kotlinx.android.synthetic.main.activity_taxi_map.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * created by Karthik A on 19/12/18
 */
class TaxiMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: TaxiViewModel
    private lateinit var poiList: ArrayList<Poi>
    private var userGesturedMove = false // to differentiate programmatic map camera animation
    private var selectedPosition: Int = -1
    private lateinit var binding: ActivityTaxiMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_taxi_map)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        initObserver()

        poiList = intent.getParcelableArrayListExtra(TaxiListFragment.PARAM_POI_LIST)
        selectedPosition = intent.getIntExtra(TaxiListFragment.PARAM_SELECTED_POSITION, -1)

        back_btn.onClick { onBackPressed() }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Util.setMapStyle(applicationContext, mMap, R.raw.map_dark_style)

        initMapListeners()

        populateTaxisOnMap()

        moveCameraToSelectedTaxiLocation()


    }


    private fun initObserver() {
        viewModel = ViewModelProviders.of(this).get(TaxiViewModel::class.java)

        viewModel.poiListResponse.observe(this, Observer {

            if (it != null) {
                this.poiList = it.poiList
                populateTaxisOnMap()
            } else {
                toast(getString(R.string.error_api))
            }
        })
    }

    /**
     * populates the taxis on the map
     */
    private fun populateTaxisOnMap() {
        for (poi in poiList) {

            var icon = if (poi.fleetType == FleetType.TAXI)
                BitmapDescriptorFactory.fromResource(R.drawable.yellow_taxi)
            else
                BitmapDescriptorFactory.fromResource(R.drawable.red_taxi)

            var marker = mMap.addMarker(MarkerOptions().position(
                    LatLng(poi.coordinate.latitude, poi.coordinate.longitude)).icon
            (icon))

            marker.tag = poi
            marker.rotation = poi.heading.toFloat()
            marker.setAnchor(0.5f, 0.5f)
        }


    }


    private fun moveCameraToSelectedTaxiLocation() {
        if (selectedPosition >= 0) {
            var selectedTaxiLatLng = LatLng(poiList[selectedPosition].coordinate.latitude,
                    poiList[selectedPosition].coordinate.longitude)

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    selectedTaxiLatLng, CITY_ZOOM_LEVEL))

            Handler().postDelayed({
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        selectedTaxiLatLng, AREA_ZOOM_LEVEL))

                showSelectedTaxiCard(poiList[selectedPosition])
            }, 2000)


        }

    }

    /**
     * new set of available taxis are updated
     */
    private fun updateAvailableTaxis() {
        var northEastLatLng = mMap.projection.visibleRegion.latLngBounds.northeast
        var southWestLatLng = mMap.projection.visibleRegion.latLngBounds.southwest

        viewModel.getPoiList(northEastLatLng.latitude, northEastLatLng.longitude,
                southWestLatLng.latitude, southWestLatLng.longitude)


    }


    /**
     * displays the details of selected taxi on card
     */
    private fun showSelectedTaxiCard(poi: Poi) {

        binding.poi = poi

        poi_info_card.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                    .alpha(1f)
                    .setDuration(500)
                    .setListener(null)

        }


    }


    private fun initMapListeners() {


        mMap.setOnCameraMoveStartedListener { reason: Int ->

            poi_info_card.visibility = View.GONE
            when (reason) {
                GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {
                    userGesturedMove = true
                }
                GoogleMap.OnCameraMoveStartedListener
                        .REASON_API_ANIMATION -> {
                }
                GoogleMap.OnCameraMoveStartedListener
                        .REASON_DEVELOPER_ANIMATION -> {
                }
            }
        }

        mMap.setOnCameraIdleListener {

            if (!userGesturedMove) {
                return@setOnCameraIdleListener
            }

            userGesturedMove = false
            mMap.clear()
            updateAvailableTaxis()
        }

        mMap.setOnMarkerClickListener {

            var poi: Poi = it.tag as Poi

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(poi.coordinate.latitude, poi.coordinate.longitude), AREA_ZOOM_LEVEL))

            showSelectedTaxiCard(poi)

            true
        }
    }
}
