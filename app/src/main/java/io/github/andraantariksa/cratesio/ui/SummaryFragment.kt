package io.github.andraantariksa.cratesio.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.andraantariksa.cratesio.R
import io.github.andraantariksa.cratesio.utils.InjectorUtils
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.coroutines.launch


class SummaryFragment : ScopedFragment() {
    private lateinit var viewModelFactory: CratesViewModelFactory
    private lateinit var viewModel: CratesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModelFactory = InjectorUtils.provideCratesViewModelFactory(context!!)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CratesViewModel::class.java)

        launch {
            val crateSummary = viewModel.crateSummary.await()
            crateSummary.observe(viewLifecycleOwner, Observer {
                textViewCratesTotalNum.text = it.numCrates.toString()
                textViewDownloadsTotalNum.text = it.numDownloads.toString()

                val newCratesAdapter =
                    CrateSummaryRecyclerViewAdapter(it.newCrates)
                recyclerViewNewCrates.layoutManager = LinearLayoutManager(context)
                recyclerViewNewCrates.adapter = newCratesAdapter

                val mostDownloadedAdapter =
                    CrateSummaryRecyclerViewAdapter(it.mostDownloaded)
                recyclerViewMostDownloaded.layoutManager = LinearLayoutManager(context)
                recyclerViewMostDownloaded.adapter = mostDownloadedAdapter

                val justUpdatedAdapter =
                    CrateSummaryRecyclerViewAdapter(it.justUpdated)
                recyclerViewJustUpdated.layoutManager = LinearLayoutManager(context)
                recyclerViewJustUpdated.adapter = justUpdatedAdapter

                val mostRecentlyDownloaded =
                    CrateSummaryRecyclerViewAdapter(it.mostRecentlyDownloaded)
                recyclerViewMostRecentlyDownloaded.layoutManager = LinearLayoutManager(context)
                recyclerViewMostRecentlyDownloaded.adapter = mostRecentlyDownloaded

                val mostPopularKeywords =
                    CrateSummaryRecyclerViewAdapter(it.mostRecentlyDownloaded)
                recyclerViewPopularKeywords.layoutManager = LinearLayoutManager(context)
                recyclerViewPopularKeywords.adapter = mostPopularKeywords

                val popularCategories =
                    CrateSummaryRecyclerViewAdapter(it.mostRecentlyDownloaded)
                recyclerPopularCategories.layoutManager = LinearLayoutManager(context)
                recyclerPopularCategories.adapter = popularCategories
            })
        }
    }
}
