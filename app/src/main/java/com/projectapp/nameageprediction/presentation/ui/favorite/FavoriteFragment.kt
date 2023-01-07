package com.projectapp.nameageprediction.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projectapp.nameageprediction.databinding.FragmentFavoriteBinding
import com.projectapp.nameageprediction.domain.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeDeleteBtnVisibility()
        setOnDeleteBtnClickListener()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModel.predictionsAdapter
        }
    }

    private fun observeDeleteBtnVisibility() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showDeleteButtonState.collect { isShowDeleteBtn ->
                    if (isShowDeleteBtn) {
                        binding.deleteSelectedBtn.visibility = View.VISIBLE
                    } else {
                        binding.deleteSelectedBtn.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setOnDeleteBtnClickListener() {
        binding.deleteSelectedBtn.setOnClickListener {
            viewModel.deleteSelectedItems()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}