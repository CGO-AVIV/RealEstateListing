package com.cgo.realestatelisting.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.cgo.realestatelisting.domain.RealEstateViewModel
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.ui.screens.RealEstateDetailsScreen
import com.cgo.realestatelisting.ui.screens.RealEstateListingScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    realEstateViewModel: RealEstateViewModel,
    realEstateResourceProvider: RealEstateResourceProvider
) {
    val controller = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = controller,
        startDestination = Destination.LISTING,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        composable(route = Destination.LISTING) {
            RealEstateListingScreen(
                viewModel = realEstateViewModel,
                controller = controller,
                resourceProvider = realEstateResourceProvider
            )
        }

        composable(route = "${Destination.DETAILS}/{$ESTATE_ID_ARG}") {
            RealEstateDetailsScreen(
                viewModel = realEstateViewModel,
                controller = controller,
                resourceProvider = realEstateResourceProvider
            )
        }
    }
}
