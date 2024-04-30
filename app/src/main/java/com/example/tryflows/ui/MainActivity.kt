package com.example.tryflows.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tryflows.R
import com.example.tryflows.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // viewModel.example()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->

                    when (uiState) {
                        is MainUIState.Error -> {
                            binding.pb.isVisible = false
                            Toast.makeText(
                                this@MainActivity,
                                "An error has occurred:${uiState.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        MainUIState.Loading -> {
                            binding.pb.isVisible = true

                        }

                        is MainUIState.Success -> {
                            binding.pb.isVisible = false
                            Toast.makeText(
                                this@MainActivity,
                                "The number of subscribers is: ${uiState.numbSubscribers}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
        }

        viewModel.example3()

    }
}