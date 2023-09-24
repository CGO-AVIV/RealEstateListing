package com.cgo.realestatelisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cgo.realestatelisting.domain.RealEstateViewModel
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.ui.navigation.Navigation
import com.cgo.realestatelisting.ui.theme.RealEstateListingTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val realEstateViewModel: RealEstateViewModel by inject()
    private val realEstateResourceProvider: RealEstateResourceProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RealEstateListingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiary
                ) {
                    Navigation(realEstateViewModel, realEstateResourceProvider)
                }
            }
        }
    }
}
