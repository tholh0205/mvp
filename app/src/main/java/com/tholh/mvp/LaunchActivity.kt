package com.tholh.mvp

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidUtilities.checkDisplaySize(this, resources.configuration)
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.activity_main)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        AndroidUtilities.checkDisplaySize(this, newConfig)
        super.onConfigurationChanged(newConfig)
    }
}