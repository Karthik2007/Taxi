package com.karthik.taxi.network

import com.karthik.taxi.model.PoiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * created by Karthik A on 18/12/18
 *
 * Client for taxi fetching url
 */
interface TaxiClient {

    @GET("/")
    fun getPoiList(@Query ("p1Lat") p1Lat: Double, @Query ("p1Lon") p1Lon: Double,
                   @Query ("p2Lat") p2Lat: Double, @Query ("p2Lon") p2Lon: Double): Call<PoiResponse>
}