package com.karthik.taxi.ui.base

import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.karthik.taxi.R
import com.karthik.taxi.manager.ConnectionManager
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton


/**
 * created by Karthik A on 18/12/18
 */
open class BaseActivity: AppCompatActivity() {

    override fun onResume() {
        super.onResume()

       checkInternetConnection()

    }


    private fun checkInternetConnection() {

        if(!ConnectionManager().isOnline(context = applicationContext))
        {
            val dialog = alert(getString(R.string.dialog_internet_desc), getString(R.string.dialog_internet_title)) {
                okButton { onResume() }
            }.show()

            dialog.setCancelable(false)
            return
        }
    }

}