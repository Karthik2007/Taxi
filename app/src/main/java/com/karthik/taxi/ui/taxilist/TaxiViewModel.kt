package com.karthik.taxi.ui.taxilist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karthik.taxi.model.PoiResponse
import com.karthik.taxi.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * created by Karthik A on 18/12/18
 */
class TaxiViewModel : ViewModel() {

    private val client = ApiClient()

    val poiListResponse = MutableLiveData<PoiResponse>()

    fun getPoiList(latitude1: Double, longitude1: Double, latitude2: Double, longitude2: Double) {


        client.provideTaxiClient().getPoiList(latitude1, longitude1, latitude2, longitude2).
                enqueue(object : Callback<PoiResponse> {

            override fun onFailure(call: Call<PoiResponse>, t: Throwable) {

                poiListResponse.postValue(null)
            }

            override fun onResponse(call: Call<PoiResponse>, response: Response<PoiResponse>) {
                if(response.code() == 200) {
                    poiListResponse.postValue(response.body())
                }else
                {
                    poiListResponse.postValue(null)
                }
            }

        })
    }
}