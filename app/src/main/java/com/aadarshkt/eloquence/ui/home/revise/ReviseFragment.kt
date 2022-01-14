package com.aadarshkt.eloquence.ui.home.revise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aadarshkt.eloquence.databinding.FragmentReviseBinding
import com.aadarshkt.eloquence.datasource.WordApplication
import com.aadarshkt.eloquence.ui.home.MainViewModel
import com.aadarshkt.eloquence.ui.home.MainViewModelFactory
import com.aadarshkt.eloquence.ui.home.revise.reviserecyclerview.ReviseAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ReviseFragment : Fragment(){

    private lateinit var binding: FragmentReviseBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((activity?.application as WordApplication).repository)
    }

    private val reviseViewModel: ReviseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val isFlippedLiveData = reviseViewModel.isFlipped

        //adapter instance
        val adapter = ReviseAdapter(onFlipChange = {
            reviseViewModel.onReviseCardClicked()
        }, isFlippedLiveData)
        binding.reviseRecyclerview.adapter = adapter

        //fill the recyclerView
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.allWords.collect {
                    adapter.submitList(it)
                }
            }
        }
    }
}

