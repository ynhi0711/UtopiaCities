package com.nhinguyen.test.utopiacities.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cavice.customer.base.BaseActivity
import com.cavice.customer.base.BaseFragmentView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 *Created by NhiNguyen on 3/24/2020.
 */

abstract class BaseFragment<T : IBaseViewModel> : DaggerFragment(), BaseFragmentView {

    @Inject
    protected lateinit var viewModel: T

    val navController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel as LifecycleObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = view ?: inflater.inflate(getLayoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    protected open fun initView() {
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner, Observer {
                if (it) {
                    showLoadingDialog()
                } else {
                    dismissLoadingDialog()
                }
            })
        }
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected open fun showLoadingDialog() {
        (activity as? BaseActivity<*>)?.showLoadingDialog()
    }

    protected open fun dismissLoadingDialog() {
        (activity as? BaseActivity<*>)?.dismissLoadingDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            dismissLoadingDialog()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val GALLERY_REQUEST = 1001
    }
}