package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.weatherapp.R
import java.lang.ref.WeakReference

class ToolbarOnDestinationChangedListener(
    toolbar: Toolbar
) : NavController.OnDestinationChangedListener {

    // TODO: medium: What is the WeakReference for?
    private val toolbarWeakRef = WeakReference(toolbar)

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

        val toolbar = toolbarWeakRef.get()

        if (toolbar == null) {
            controller.removeOnDestinationChangedListener(this)
            return
        }

        toolbar.apply {
            setNavigationOnClickListener {
                if (destination.id == R.id.weatherDetailsFragment) {
                    controller.navigateUp()
                }
            }
        }
    }
}
