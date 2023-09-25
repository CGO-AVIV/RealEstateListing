package com.cgo.real_estate_listing_domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.cgo.realestatelisting.data.model.RealEstateData
import com.cgo.realestatelisting.data.model.RealEstateListing
import com.cgo.realestatelisting.domain.RealEstateViewModel
import com.cgo.realestatelisting.domain.SingleState
import com.cgo.realestatelisting.domain.State
import com.cgo.realestatelisting.domain.mapper.toRealEstate
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.domain.usecases.GetRealEstateListingUseCase
import com.cgo.realestatelisting.domain.usecases.GetRealEstateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RealEstateViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcher = TestDispatcherProvider

    @Mock
    private lateinit var getRealEstateListingUseCase: GetRealEstateListingUseCase

    @Mock
    private lateinit var getRealEstateUseCase: GetRealEstateUseCase

    @Mock
    private lateinit var realEstateResourceProvider: RealEstateResourceProvider

    @Mock
    private lateinit var observer: Observer<State>

    @Mock
    private lateinit var detailsObserver: Observer<SingleState>

    private lateinit var viewModel: RealEstateViewModel

    private val testListing = RealEstateListing(
        items = listOf(
            RealEstateData(
                bedroomsCount = 4,
                cityName = "Villers-sur-Mer",
                id = 1,
                areaCode = 250,
                imageUrl = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
                price = 1500000,
                professionalName = "GSL EXPLORE",
                propertyType = "Maison - Villa",
                offerType = 1,
                roomsCount = 8
            ),
            RealEstateData(
                bedroomsCount = null,
                cityName = "Nice",
                id = 4,
                areaCode = 250,
                imageUrl = "https://v.seloger.com/s/crop/590x330/visuels/1/9/f/x/19fx7n4og970dhf186925d7lrxv0djttlj5k9dbv8.jpg",
                price = 5000000,
                professionalName = "GSL CONTACTING",
                propertyType = "Maison - Villa",
                offerType = 3,
                roomsCount = null
            )
        )
    )

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher.main())
        viewModel = RealEstateViewModel(
            getRealEstateListingUseCase,
            getRealEstateUseCase,
            realEstateResourceProvider
        ).apply {
            state.observeForever(observer)
            singleState.observeForever(detailsObserver)
        }
    }

    @After
    fun clear() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a successful listing fetch, the state goes from Loading to Content`() = runTest {
        val items = testListing.items.map {
            it.toRealEstate("gils")
        }
        whenever(getRealEstateListingUseCase.getRealEstateListing())
            .thenReturn(flowOf(items))
        viewModel.onRealEstateListingRequested()
        advanceUntilIdle()

        verify(observer).onChanged(State.Loading)
        verify(observer).onChanged(State.ListContent(items))
    }

    @Test
    fun `Given a successful listing fetch without items, the state goes from Loading to Error with an empty message`() = runTest {
        whenever(getRealEstateListingUseCase.getRealEstateListing())
            .thenReturn(flowOf(emptyList()))
        whenever(realEstateResourceProvider.emptyLabel)
            .thenReturn("empty")
        viewModel.onRealEstateListingRequested()
        advanceUntilIdle()

        verify(observer).onChanged(State.Loading)
        verify(observer).onChanged(State.Error("empty"))
    }

    @Test
    fun `Given a failed listing fetch, the state goes from Loading to Error with an error message`() = runTest {
        val exception = IOException("crash")
        whenever(getRealEstateListingUseCase.getRealEstateListing())
            .thenReturn(flow { throw exception })
        whenever(realEstateResourceProvider.errorLabel)
            .thenReturn("error")
        viewModel.onRealEstateListingRequested()
        advanceUntilIdle()

        verify(observer).onChanged(State.Loading)
        verify(observer).onChanged(State.Error("error"))
    }

    @Test
    fun `Given a successful details fetch, the state goes from Loading to Content`() = runTest {
        val item = testListing.items.first().toRealEstate("gils")
        whenever(getRealEstateUseCase.getRealEstate(any()))
            .thenReturn(flowOf(item))
        viewModel.onRealEstateItemRequested(any())
        advanceUntilIdle()

        verify(detailsObserver).onChanged(SingleState.Loading)
        verify(detailsObserver).onChanged(SingleState.SingleContent(item))
    }

    @Test
    fun `Given a failed details fetch, the state goes from Loading to Error with an error message`() = runTest {
        val exception = IOException("crash")
        whenever(getRealEstateUseCase.getRealEstate(any()))
            .thenReturn(flow { throw exception })
        whenever(realEstateResourceProvider.errorLabel)
            .thenReturn("error")
        viewModel.onRealEstateItemRequested(any())
        advanceUntilIdle()

        verify(detailsObserver).onChanged(SingleState.Loading)
        verify(detailsObserver).onChanged(SingleState.Error("error"))
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
object TestDispatcherProvider {
    private val scheduler = TestCoroutineScheduler()
    fun main(): CoroutineDispatcher = StandardTestDispatcher(scheduler)
}
