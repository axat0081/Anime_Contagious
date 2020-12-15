package com.example.animecontagious.ui

import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.animecontagious.adapter.AnimeLoadStateAdapter
import com.example.animecontagious.adapter.UpcomingAnimeAdapter
import com.example.animecontagious.R
import com.example.animecontagious.data.AnimeResponse
import com.example.animecontagious.data.AnimeViewModel
import com.example.animecontagious.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), UpcomingAnimeAdapter.OnItemClickListener {
    private val viewModel by viewModels<AnimeViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)
        val adapter = UpcomingAnimeAdapter(this)
        binding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.itemAnimator = null
            recyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = AnimeLoadStateAdapter { adapter.retry() },
                footer = AnimeLoadStateAdapter { adapter.retry() }
            )
            retryBtn.setOnClickListener {
                adapter.retry()
            }
        }
        viewModel.animeUpcomingData.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerview.isVisible = loadState.source.refresh !is LoadState.Loading
                retryBtn.isVisible = loadState.source.refresh is LoadState.Error
                txtError.isVisible = loadState.source.refresh is LoadState.Error
                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerview.isVisible = false
                    noResultsTxt.isVisible = true
                } else {
                    noResultsTxt.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_NUMBER
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val num: Long = query.toLong()
                    if (num > 8) {
                        Toast.makeText(
                            context,
                            "This page number is not available ",
                            Toast.LENGTH_LONG
                        ).show()
                        return true
                    }
                    binding.recyclerview.scrollToPosition(0)
                    viewModel.getUpcomingAnimeResults(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemCLick(anime: AnimeResponse.Anime) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(anime)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
