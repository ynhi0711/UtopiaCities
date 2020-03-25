package com.nhinguyen.test.utopiacities.ui.main.activity

import com.cavice.customer.base.BaseActivity
import com.nhinguyen.test.utopiacities.R
import com.nhinguyen.test.utopiacities.base.EmptyViewModel
import com.nhinguyen.test.utopiacities.data.DBHelper
import com.nhinguyen.test.utopiacities.data.DataBase
import com.nhinguyen.test.utopiacities.data.DatabaseManger
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity<EmptyViewModel>() {

    lateinit var dbHelper: DBHelper

    override fun getLayoutRes(): Int = R.layout.activity_main
    override fun initView() {
        super.initView()
        dbHelper = DBHelper(this@MainActivity)
        dbHelper.apply {
            createDatabase()
            openDatabase()
            Timber.e("${getCities(10).size}")
        }

//        Timber.d("sjkdhfkjhdkafh ${DatabaseManger(this).monHoc.size}")

    }

}
