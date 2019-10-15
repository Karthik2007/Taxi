package com.karthik.taxi.ui.taxilist

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.karthik.taxi.R
import com.karthik.taxi.listener.OnItemClickListener
import com.karthik.taxi.model.Poi
import com.karthik.taxi.utils.AppConstants.Companion.DEFAULT_LAT1
import com.karthik.taxi.utils.AppConstants.Companion.DEFAULT_LAT2
import com.karthik.taxi.utils.AppConstants.Companion.DEFAULT_LON1
import com.karthik.taxi.utils.AppConstants.Companion.DEFAULT_LON2
import kotlinx.android.synthetic.main.fragment_taxi_list.*
import org.jetbrains.anko.support.v4.toast
import com.karthik.taxi.ui.taximap.TaxiMapActivity


/**
 * A placeholder fragment containing a simple view.
 */
class TaxiListFragment : androidx.fragment.app.Fragment(), OnItemClickListener{

    private lateinit var viewModel: TaxiViewModel
    private lateinit var taxiAdapter: TaxiListAdapter
    private var poiList: ArrayList<Poi> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_taxi_list, container, false)

        viewModel = ViewModelProviders.of(this).get(TaxiViewModel::class.java)

        initObserver()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        taxiAdapter = TaxiListAdapter(this)
        taxi_recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.context)

        val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        taxi_recycler_view.apply {
            layoutAnimation = animation
            adapter = taxiAdapter
        }

        swipe_container.isRefreshing = true
        viewModel.getPoiList(DEFAULT_LAT1, DEFAULT_LON1, DEFAULT_LAT2, DEFAULT_LON2)


        // pull to refresh listener
        swipe_container.setOnRefreshListener {

            viewModel.getPoiList(DEFAULT_LAT1, DEFAULT_LON1, DEFAULT_LAT2, DEFAULT_LON2)
        }
    }

    private fun initObserver() {
        viewModel.poiListResponse.observe(this, Observer {

            if(it != null)
            {
                setTaxiList(it.poiList)

            }else
            {
                toast(getString(R.string.error_api))
            }
            swipe_container.isRefreshing = false

        })
    }

    /**
     * populates the recycler adapter with list of PoIs
     */
    private fun setTaxiList(poiList: ArrayList<Poi>) {
        this.poiList = poiList
        taxiAdapter.submitList(poiList)
        taxi_recycler_view.scheduleLayoutAnimation()

    }

    override fun onItemClick(position: Int, data: Any) {

        var intent = Intent(context, TaxiMapActivity::class.java)
        intent.putParcelableArrayListExtra(PARAM_POI_LIST, poiList)
        intent.putExtra(PARAM_SELECTED_POSITION, position)
        startActivity(intent)

    }

    companion object {
        const val PARAM_POI_LIST = "poi_list"
        const val PARAM_SELECTED_POSITION = "selected_pos"
    }

}
