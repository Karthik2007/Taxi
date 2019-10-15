package com.karthik.taxi.utils

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MapStyleOptions


/**
 * created by Karthik A on 19/12/18
 */
class Util {

    companion object {

        /**
         * @param googleMap
         */
        fun setMapStyle(ctx: Context, googleMap: GoogleMap, resId: Int) {
            try {
                // Customise the styling of the base map using a JSON object defined
                // in a raw resource file.
                val success = googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(
                                ctx, resId))

                if (!success) {
                    Log.e("Map", "Style parsing failed.")
                }
            } catch (e: Resources.NotFoundException) {
                Log.e("Map", "Can't find style. Error: ", e)
            }

        }

    }

}