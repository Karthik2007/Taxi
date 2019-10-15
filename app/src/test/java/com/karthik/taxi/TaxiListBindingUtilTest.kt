package com.karthik.taxi

import android.view.View
import com.karthik.taxi.model.FleetType
import com.karthik.taxi.ui.taxilist.TaxiListBindingUtil
import org.junit.Assert.assertEquals
import org.junit.Test


/**
 * created by Karthik A on 19/12/18
 */
class TaxiListBindingUtilTest {

    @Test
    fun headingDirectionTest()
    {
        // list of test heading directions
        var headingDirectionList = listOf(300.5,45.7,23.3,66.2,77.5,33.2,160.4,230.1,275.0, 290.1, 20.234)

        var expected = listOf("North-West","North-East","North-East","North-East"
                ,"East","North-East","South","South-West","West","West","North")

        for(index in headingDirectionList.indices)
        {
            assertEquals(expected[index],TaxiListBindingUtil.headingDirection(headingDirectionList[index]))

        }
    }

    @Test
    fun isPoolingTest()
    {
        var testFleet = "POOLING"

        assertEquals(View.VISIBLE, TaxiListBindingUtil.isPooling(testFleet))
        assertEquals(View.VISIBLE, TaxiListBindingUtil.isPooling(FleetType.POOLING.type))
    }

    @Test
    fun isPoolingTest2()
    {
        var testFleet = "TAXI"

        assertEquals(View.GONE, TaxiListBindingUtil.isPooling(testFleet))
        assertEquals(View.GONE, TaxiListBindingUtil.isPooling(FleetType.TAXI.type))
    }

    @Test
    fun getLocationStringTest()
    {
        assertEquals("15.569 - 51.000",TaxiListBindingUtil.getLocationString(15.5688976,50.99976746))
        assertEquals("15.000 - 50.000",TaxiListBindingUtil.getLocationString(15.0000,50.0))
    }
}