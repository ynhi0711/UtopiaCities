package com.nhinguyen.test.utopiacities.ui.main.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.nhinguyen.test.utopiacities.R
import com.nhinguyen.test.utopiacities.base.BaseFragment
import com.nhinguyen.test.utopiacities.data.DBHelper
import com.nhinguyen.test.utopiacities.ui.main.fragment.adapter.UtopiaCitiesAdapter
import kotlinx.android.synthetic.main.fragment_utopia_cities.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by NhiNguyen on 3/24/2020.
 */

class UtopiaFragment : BaseFragment<UtopiaCitiesViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_utopia_cities
    @Inject
    lateinit var dbHelper: DBHelper
    lateinit var adapter: UtopiaCitiesAdapter

    override fun initView() {
        super.initView()
        Timber.d("TAGGGG===> ,${dbHelper.getCities(10).size}")

        adapter = UtopiaCitiesAdapter(viewModel.cities.value) {
            //OnClickItem
        }
        rvCities?.adapter = adapter
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
            adapter.notifyDataSetChanged()
            if (swipeLayout?.isRefreshing == true) swipeLayout?.isRefreshing = false
        })
        viewModel.getCities(true)
    }
}