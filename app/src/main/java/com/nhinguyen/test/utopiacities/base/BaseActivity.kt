package com.cavice.customer.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.nhinguyen.test.utopiacities.base.IBaseViewModel
import com.nhinguyen.test.utopiacities.custom_view.LoadingProgress
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 *Created by NhiNguyen on 3/24/2020.
 */

abstract class BaseActivity<T : IBaseViewModel> : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModel: T

    private var loadingProgress: LoadingProgress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        lifecycle.addObserver(viewModel as LifecycleObserver)
        initView()
    }

    /**
     * Define the layout res id can be used to [Activity.setContentView]
     *
     * @return the layout res id
     */
    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    /**
     * Init [View] components here. Such as set adapter for [RecyclerView], set listener
     * or anything else
     */
    protected open fun initView() {
        viewModel.error.observe(this, Observer {

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            loadingProgress?.let {
                if (it.isShowing) {
                    it.dismiss()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun showLoadingDialog() {
        if (loadingProgress == null) {
            loadingProgress = LoadingProgress(this)
        }
        loadingProgress?.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    open fun dismissLoadingDialog() {
        try {
            loadingProgress?.let {
                it.dismiss()
                loadingProgress = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}