package com.nhinguyen.test.utopiacities.ui.main.fragment

import android.os.Handler
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhinguyen.test.utopiacities.R
import com.nhinguyen.test.utopiacities.base.BaseFragment
import com.nhinguyen.test.utopiacities.custom_view.OnLoadMoreListener
import com.nhinguyen.test.utopiacities.custom_view.RecyclerViewLoadMore
import com.nhinguyen.test.utopiacities.ui.main.fragment.adapter.UtopiaCitiesAdapter
import kotlinx.android.synthetic.main.fragment_utopia_cities.*

/**
 * Created by NhiNguyen on 3/24/2020.
 */

class UtopiaFragment : BaseFragment<UtopiaCitiesViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_utopia_cities
    lateinit var adapter: UtopiaCitiesAdapter
    lateinit var scrollListener: RecyclerViewLoadMore
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun initView() {
        super.initView()
        adapter = UtopiaCitiesAdapter {
            //OnClickItem
        }
        rvCities?.adapter = adapter
        //** Set the Layout Manager of the RecyclerView
        setRVLayoutManager()
        //** Set the scrollListerner of the RecyclerView
        setRVScrollListener()
        initViewModel()
        rvCities?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getCities(false)
                }
            }
        })
        swipeLayout?.setOnRefreshListener { viewModel.getCities(true) }
    }

    private fun initViewModel() {
        viewModel.cities.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.updateData(it) }
            if (swipeLayout?.isRefreshing == true) swipeLayout?.isRefreshing = false
        })
        viewModel.getCities(true)
    }

    private fun setRVLayoutManager() {
        mLayoutManager = LinearLayoutManager(context)
        rvCities?.layoutManager = mLayoutManager
        rvCities?.setHasFixedSize(true)
    }

    private fun setRVScrollListener() {
        mLayoutManager = LinearLayoutManager(context)
        scrollListener = RecyclerViewLoadMore(mLayoutManager as LinearLayoutManager)
        scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
        rvCities?.addOnScrollListener(scrollListener)
    }

    private fun loadMoreData() {
        adapter.addLoadingView()
        viewModel.getCities(false) { isRefresh, list ->
            Handler().postDelayed({
                if (!isRefresh) {
                    adapter.removeLoadingView()
                    adapter.addData(list.toCollection(ArrayList()))
                    scrollListener.setLoaded()
                }
            }, 1000)
        }
    }

}