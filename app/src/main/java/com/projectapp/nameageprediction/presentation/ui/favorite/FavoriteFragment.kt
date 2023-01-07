package com.projectapp.nameageprediction.presentation.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectapp.nameageprediction.R
import com.projectapp.nameageprediction.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        viewModel.observeFavoritesList()
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
            showDeleteDialogue()
        }
    }

    private fun showDeleteDialogue() {
        AlertDialog.Builder(context)
            .setTitle(R.string.delete_name_subject_text)
            .setMessage(R.string.delete_name_decription_text)
            .setPositiveButton(R.string.delete_answer_yes_text) { dialog, _ ->
                viewModel.deleteSelectedItemsFromFavorite()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.delete_answer_no_text) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null

    }
}
