package com.example.primohomepage.presentation.feature.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.primohomepage.R
import com.example.primohomepage.databinding.ActivityHomeBinding
import com.example.primohomepage.domain.model.HomeScreenEffect
import com.example.primohomepage.presentation.feature.detail.DetailWebViewActivity
import com.example.primohomepage.presentation.feature.home.adapter.ArticleListAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val articleAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter {
            DetailWebViewActivity.openScreen(
                context = this@HomeActivity,
                urlToLoad = it.detailLink
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initObserver()
        viewModel.getFeeds()
    }

    private fun initObserver() {
        viewModel.homeScreenState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            articleAdapter.submitList(it.articleModels)
        }.launchIn(lifecycleScope)


        viewModel.homeScreenEffect.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            when(it) {
                HomeScreenEffect.OnErrorFullScreen -> {
                    Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG)
                        .show()
                    Log.d("called error", "show error")
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initView() = with(binding) {
        articlesRecycleView.adapter = articleAdapter
    }
}