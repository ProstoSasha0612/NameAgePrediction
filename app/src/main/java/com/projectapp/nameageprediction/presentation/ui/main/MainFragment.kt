package com.projectapp.nameageprediction.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.projectapp.nameageprediction.databinding.FragmentMainBinding
import com.projectapp.nameageprediction.domain.repository.Repository
import com.projectapp.nameageprediction.domain.usecases.LoadNameAgeUseCase
import com.projectapp.nameageprediction.presentation.hideKeyBoard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var useCase: LoadNameAgeUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAgePredictionState()
        setOnClickListeners()
    }

    private fun searchPrediction(name: String) {
        viewModel.searchPrediction(name)
    }

    private fun setOnClickListeners() {
        setOnSearchClickListener()
    }

    private fun setOnSearchClickListener() {
//        binding.searchview.setOnSearchClickListener {
//            Log.d("MYTAG","on search clicked")
//            Log.d("MYTAG","${binding.searchview.query}")
//            viewModel.searchPrediction(binding.searchview.query.toString())
//        }

        binding.searchview.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(name: String?): Boolean {
                Log.d("MYTAG", "on search clicked")
                Log.d("MYTAG", "$name")
                viewModel.searchPrediction(name?.trim())

                view?.hideKeyBoard()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }


    private fun observeAgePredictionState() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.agePredictionState.collect { state ->
                    when (state) {
                        is AgePredictionState.Empty -> {
                            with(binding) {
                                ageCircleTextview.visibility = View.GONE
                                addToFavoriteBtn.visibility = View.GONE
                                shareBtn.visibility = View.GONE
                                cardWriteNameTextview.visibility = View.VISIBLE
                            }

                        }
                        is AgePredictionState.Success -> {
                            with(binding) {
                                ageCircleTextview.visibility = View.VISIBLE
                                ageCircleTextview.text = state.age.toString()
                                addToFavoriteBtn.visibility = View.VISIBLE
                                shareBtn.visibility = View.VISIBLE
                                cardWriteNameTextview.visibility = View.GONE
                            }
                        }
                        is AgePredictionState.Error -> {
                            with(binding) {
                                ageCircleTextview.visibility = View.GONE
                                addToFavoriteBtn.visibility = View.GONE
                                shareBtn.visibility = View.GONE

                                cardWriteNameTextview.visibility = View.VISIBLE
                                cardWriteNameTextview.text = state.message
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
