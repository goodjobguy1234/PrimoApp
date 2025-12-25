package com.example.primohomepage

import app.cash.turbine.test
import com.example.primohomepage.data.PrimoFeedDao
import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.entity.PrimoFeedEntity
import com.example.primohomepage.data.repository.PrimoFeedsRepositoryImpl
import com.example.primohomepage.domain.GetArticleFeedUseCase
import com.example.primohomepage.domain.model.ArticleModel
import com.example.primohomepage.domain.model.PrimoInfoModel
import com.example.primohomepage.presentation.feature.home.HomeViewModel
import com.squareup.moshi.Moshi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getArticleFeedUseCase = mockk<GetArticleFeedUseCase>()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        // Set the Coroutine Dispatcher for testing
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(getArticleFeedUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetFeeds_Success() = runTest {
        coEvery { getArticleFeedUseCase.invoke() } returns Pair(
            PrimoInfoModel(
                title = "Local Info",
                desc = "",
                image = ""
            ), listOf<ArticleModel>()
        )

        viewModel.homeScreenState.test {
            viewModel.getFeeds()
            advanceUntilIdle()

            val initialState = awaitItem()
            assert(initialState.articleModels.isEmpty())

            val localState = awaitItem()
            assert(localState.primoInfo?.title == "Local Info")

            cancelAndIgnoreRemainingEvents()
        }
    }
}