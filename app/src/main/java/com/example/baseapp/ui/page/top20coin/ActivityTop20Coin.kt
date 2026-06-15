package com.example.baseapp.ui.page.top20coin

import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baseapp.data.remote.Resource
import com.example.baseapp.databinding.ActivityTop20CoinBinding
import com.example.baseapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityTop20Coin : BaseActivity<ActivityTop20CoinBinding>() {
    private val viewModel: ViewModelCoin by viewModels()

    private lateinit var adapter: AdapterCoin

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTop20CoinBinding {
        return ActivityTop20CoinBinding.inflate(layoutInflater)
    }

    override fun initData(intent: Intent) {
        viewModel.getTop20Coins()
    }

    override fun initView() {
        setupRecyclerView()
    }

    override fun initAction() {

    }

    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.top20State.collect { resource ->
                    when (resource) {

                        is Resource.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.recyclerView.isVisible = false
                            binding.tvError.isVisible = false
                        }

                        is Resource.Success -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerView.isVisible = true
                            binding.tvError.isVisible = false
                            adapter.submitList(resource.data)
                        }

                        is Resource.Error -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerView.isVisible = false
                            binding.tvError.isVisible = true
                            binding.tvError.text = resource.message
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = AdapterCoin()
        binding.recyclerView.adapter = adapter
    }
}