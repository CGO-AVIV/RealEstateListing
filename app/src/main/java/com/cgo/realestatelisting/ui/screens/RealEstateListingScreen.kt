package com.cgo.realestatelisting.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.cgo.realestatelisting.domain.RealEstateViewModel
import com.cgo.realestatelisting.domain.State
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.ui.composables.ActionableMessage
import com.cgo.realestatelisting.ui.composables.IndeterminateProgress
import com.cgo.realestatelisting.ui.composables.LabelText
import com.cgo.realestatelisting.ui.composables.ValueText
import com.cgo.realestatelisting.ui.navigation.Destination
import com.cgo.realestatelisting.ui.theme.AppTheme
import com.cgo.realestatelisting.ui.theme.Favorite
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealEstateListingScreen(
    viewModel: RealEstateViewModel,
    controller: NavController,
    resourceProvider: RealEstateResourceProvider
) {
    val state = viewModel.state.observeAsState(initial = State.Loading).value

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                ),
                title = {
                    Text(
                        text = resourceProvider.listTitleLabel,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->
        when (state) {
            State.Loading -> {
                IndeterminateProgress()
            }

            is State.ListContent -> {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = AppTheme.dimens.big),
                    modifier = Modifier
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = 0.dp
                        )
                ) {
                    items(state.items) { item ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.secondary
                            ),
                            elevation = CardDefaults.cardElevation(),
                            modifier = Modifier
                                .padding(
                                    top = AppTheme.dimens.big,
                                    start = AppTheme.dimens.big,
                                    end = AppTheme.dimens.big
                                )
                                .fillMaxSize()
                                .clickable {
                                    viewModel.onRealEstateItemRequested(item.id)
                                    controller.navigate("${Destination.DETAILS}/${item.id}")
                                }
                        ) {
                            Column(modifier = Modifier.padding(AppTheme.dimens.big)) {
                                Row {
                                    item.imageUrl?.let { url ->
                                        val request = ImageRequest.Builder(LocalContext.current)
                                            .data(url)
                                            .crossfade(true)
                                            .build()
                                        Image(
                                            contentDescription = "${item.propertyType} ${item.price}",
                                            modifier = Modifier
                                                .padding(end = AppTheme.dimens.small)
                                                .width(240.dp)
                                                .height(150.dp)
                                                .background(color = randomizeFavoriteEstate())
                                                .clip(
                                                    shape = RoundedCornerShape(
                                                        topStart = 48.dp
                                                    )
                                                )
                                                .padding(0.dp),
                                            painter = rememberAsyncImagePainter(request),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    Column {
                                        LabelText(text = resourceProvider.cityLabel)
                                        ValueText(text = item.cityName)
                                        LabelText(text = resourceProvider.estateTypeLabel)
                                        ValueText(text = item.propertyType)
                                    }
                                }
                                Row(
                                    modifier = Modifier.padding(top = AppTheme.dimens.verySmall)
                                ) {
                                    LabelText(text = resourceProvider.priceLabel)
                                    ValueText(text = item.price)
                                }
                            }
                        }
                    }
                }
            }

            is State.Error -> {
                ActionableMessage(
                    message = state.message,
                    buttonMessage = resourceProvider.retryLabel,
                    actionable = viewModel::onRealEstateListingRequested
                )
            }
        }
    }
}

@Composable
private fun randomizeFavoriteEstate(): Color {
    val isFavorite = Random(System.currentTimeMillis()).nextLong() % 2 == 0L
    return if (isFavorite) {
        Favorite
    } else {
        MaterialTheme.colorScheme.secondary
    }
}