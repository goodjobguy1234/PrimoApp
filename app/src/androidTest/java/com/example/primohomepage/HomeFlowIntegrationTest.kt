package com.example.primohomepage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.primohomepage.data.PrimoFeedDao
import com.example.primohomepage.data.PrimoFeedsRemoteDatasource
import com.example.primohomepage.data.entity.PrimoFeedEntity
import com.example.primohomepage.data.repository.PrimoFeedsRepositoryImpl
import com.example.primohomepage.domain.ArticleFeedsRepository
import com.example.primohomepage.domain.GetArticleFeedUseCase
import com.example.primohomepage.fake.FakeRemoteDataSource
import com.example.primohomepage.presentation.feature.home.HomeViewModel
import com.squareup.moshi.Moshi
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeFlowIntegrationTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val mockDao = mockk<PrimoFeedDao>(relaxed = true)
    private val moshi = Moshi.Builder().build()

    private lateinit var repository: ArticleFeedsRepository
    private lateinit var getArticleFeedUseCase: GetArticleFeedUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
//noted This is a logic integration test. But due to problem of device-specific JVMTI agent restrictions on physical hardware it can't run some device
        Dispatchers.setMain(testDispatcher)
        val fakeRemote = FakeRemoteDataSource(service = mockk(relaxed = true))
        repository = PrimoFeedsRepositoryImpl(fakeRemote, mockDao, moshi)
        getArticleFeedUseCase = GetArticleFeedUseCase(repository)

        viewModel = HomeViewModel(
            getArticleFeedUseCase = getArticleFeedUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testViewModelFlow_UpdatesFromLocalThenRemote() = runTest {
        val localEntity = PrimoFeedEntity(
            uuid = "123",
            feedInfo = "Local Info",
            articleList = "[]"
        )
        every { mockDao.getPrimoFeeds() } returns flowOf(localEntity)

//        val remoteResponse = mockk<PrimoFeedDataResponse>() {
//
//        }
//        coEvery { mockRemote.getFeeds() } returns remoteResponse

        viewModel.homeScreenState.test {
            viewModel.getFeeds()
            val initialState = awaitItem()
            assert(initialState.articleModels.isEmpty())

            val localState = awaitItem()
            assert(localState.primoInfo?.title == "Local Info")

            coVerify(exactly = 1) {
                getArticleFeedUseCase.invoke()
                repository.getFeeds()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}