package com.projectapp.nameageprediction.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.projectapp.nameageprediction.R
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
    lateinit var loadNameAgeUseCase: LoadNameAgeUseCase

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

    private fun setOnClickListeners() {
        setOnSearchClickListener()
        setOnFavoriteBtnClickListener()
        setOnShareBtnClickListener()
    }

    private fun setOnFavoriteBtnClickListener() {
        binding.addToFavoriteBtn.setOnClickListener {
            val prediction =
                (viewModel.agePredictionState.value as? AgePredictionState.Success)?.prediction
            prediction?.let {
                viewModel.addToFavorite(prediction)
                Toast.makeText(requireActivity(),
                    "Предсказание для  ${prediction.name} добавлено в избранное",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnShareBtnClickListener() {
        binding.shareBtn.setOnClickListener {
            shareAgePrediction()
        }
    }

    private fun setOnSearchClickListener() {
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

    private fun shareAgePrediction() {
        val prediction =
            (viewModel.agePredictionState.value as? AgePredictionState.Success)?.prediction
        prediction?.let {
            val textToShare = String.format(
                getString(R.string.share_prediction_text),
                prediction.name,
                prediction.age
            )
            val subjectText =
                String.format(getString(R.string.share_prediction_subject_text), prediction.name)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, subjectText)
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }
            startActivity(intent)
        }
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
                                ageCircleTextview.text = state.prediction.age.toString()
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
