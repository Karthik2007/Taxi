package com.karthik.taxi.ui.taxilist

import androidx.databinding.BindingAdapter
import android.view.View
import com.karthik.taxi.model.FleetType


/**
 * created by Karthik A on 19/12/18
 */
class TaxiListBindingUtil {


    companion object {

        @JvmStatic
        fun getLocationString(lat: Double, lon :Double): String
        {
            return String.format("%.3f",lat) + " - "+String.format("%.3f",lon)
        }

        @JvmStatic
        fun isPooling(fleetType: String?): Int
        {
            return if(fleetType == FleetType.POOLING.type) View.VISIBLE else View.GONE
        }

        @JvmStatic
        fun headingDirection(heading: Double): String
        {
            var directionsList = listOf("North", "North-East", "East", "South-East",
                    "South", "South-West", "West", "North-West")

            var index = ((heading + 22.5)/45).toInt() and 7

            return directionsList[index]
        }


        @JvmStatic
        @BindingAdapter("testBindingAdapter")
        fun testBindingColies(view: View, value: Int)
        {

        }
    }
}