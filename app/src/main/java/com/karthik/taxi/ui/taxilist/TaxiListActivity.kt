package com.karthik.taxi.ui.taxilist

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.karthik.taxi.R
import com.karthik.taxi.ui.base.BaseActivity

import kotlinx.android.synthetic.main.activity_home.*

class TaxiListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

}
