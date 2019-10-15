package com.karthik.taxi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * created by Karthik A on 18/12/18
 */
@Parcelize
data class Poi(val coordinate: Coordinate,
               val id: Int,
               val heading: Double,
               val fleetType: FleetType) : Parcelable

@Parcelize
data class Coordinate(val latitude: Double,
                      val longitude: Double) : Parcelable


enum class FleetType(val type: String)
{
    TAXI("TAXI"),
    POOLING("POOLING")
}