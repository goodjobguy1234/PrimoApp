package com.example.primohomepage.feature.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.primohomepage.R
import com.example.primohomepage.databinding.ActivityHomeBinding
import com.example.primohomepage.feature.detail.DetailWebViewActivity
import com.example.primohomepage.feature.home.adapter.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.homeScreenState.collect {
                    articleAdapter.submitList(it.articleModels)

                    it.primoInfo?.let {
                        //todo show default home screen
                    }
                }
            }
        }
    }

    private fun initView() = with(binding) {
        articlesRecycleView.adapter = articleAdapter
    }
}