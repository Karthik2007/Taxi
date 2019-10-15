package com.karthik.taxi.ui.splash

import android.os.Bundle
import android.os.Handler
import com.karthik.taxi.R
import com.karthik.taxi.ui.base.BaseActivity
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.karthik.taxi.ui.taxilist.TaxiListActivity
import kotlinx.android.synthetic.main.activity_splash.*


/**
 * created by Karthik A on 18/12/18
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            startActivity(Intent(applicationContext, TaxiListActivity::class.java))
            finish()
        }, 5000)
    }

    override fun onResume() {
        super.onResume()
        animateSplashImage()
    }


    private fun animateSplashImage() {
        val bounceAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        splash_image.startAnimation(bounceAnimation)
    }

}
