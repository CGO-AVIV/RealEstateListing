package com.cgo.realestatelisting.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.cgo.realestatelisting.domain.RealEstateViewModel
import com.cgo.realestatelisting.domain.SingleState
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.ui.composables.ActionableMessage
import com.cgo.realestatelisting.ui.composables.IndeterminateProgress
import com.cgo.realestatelisting.ui.composables.LabelText
import com.cgo.realestatelisting.ui.composables.ValueText
import com.cgo.realestatelisting.ui.navigation.ESTATE_ID_ARG
import com.cgo.realestatelisting.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealEstateDetailsScreen(
    viewModel: RealEstateViewModel,
    controller: NavController,
    resourceProvider: RealEstateResourceProvider
) {
    val state = viewModel.singleState.observeAsState(initial = SingleState.Loading).value

    fun retry() {
        val id = controller.currentBackStackEntry?.arguments?.getString(ESTATE_ID_ARG) ?: return
        viewModel.onRealEstateItemRequested(id.toInt())
    }

    when (state) {
        SingleState.Loading -> {
            IndeterminateProgress()
        }

        is SingleState.SingleContent -> {
            val detailedItem = state.item
            LazyColumn(contentPadding = PaddingValues(bottom = AppTheme.dimens.big)) {
                item {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.tertiary
                        ),
                        title = {
                            Text(
                                text = "${detailedItem.propertyType} ${resourceProvider.atLabel} ${detailedItem.cityName}",
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { controller.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = resourceProvider.backLabel
                                )
                            }
                        }
                    )
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
                    ) {
                        Column {
                            detailedItem.imageUrl?.let { url ->
                                val request = ImageRequest.Builder(LocalContext.current)
                                    .data(url)
                                    .size(Size.ORIGINAL)
                                    .build()
                                Image(
                                    contentDescription = "${detailedItem.propertyType} ${detailedItem.price}",
                                    painter = rememberAsyncImagePainter(request),
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                            Column(
                                modifier = Modifier.padding(top = AppTheme.dimens.small)
                            ) {
                                Row {
                                    LabelText(text = resourceProvider.cityLabel)
                                    ValueText(text = detailedItem.cityName)
                                }
                                Row {
                                    LabelText(text = resourceProvider.estateTypeLabel)
                                    ValueText(text = detailedItem.propertyType)
                                }
                                Row {
                                    LabelText(text = resourceProvider.priceLabel)
                                    ValueText(text = detailedItem.price)
                                }
                                Row {
                                    LabelText(text = resourceProvider.roomsCountLabel)
                                    ValueText(text = detailedItem.roomsCount ?: resourceProvider.notApplicableLabel)
                                }
                                Row {
                                    LabelText(text = resourceProvider.bedroomsCountLabel)
                                    ValueText(text = detailedItem.bedroomsCount ?: resourceProvider.notApplicableLabel)
                                }
                                Row {
                                    LabelText(text = resourceProvider.professionalLabel)
                                    ValueText(text = detailedItem.professionalName)
                                }
                            }
                        }
                    }
                }
            }
        }

        is SingleState.Error -> {
            ActionableMessage(
                message = state.message,
                buttonMessage = resourceProvider.retryLabel,
                actionable = ::retry
            )
        }
    }
}
