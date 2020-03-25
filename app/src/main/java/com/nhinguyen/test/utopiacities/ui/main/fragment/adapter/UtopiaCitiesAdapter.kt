package com.nhinguyen.test.utopiacities.ui.main.fragment.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhinguyen.test.utopiacities.R
import com.nhinguyen.test.utopiacities.model.City
import com.nhinguyen.test.utopiacities.util.Constant
import kotlinx.android.synthetic.main.item_utopia_cities.view.*
import java.text.DecimalFormat


/**
 * Created by NhiNguyen on 3/24/2020.
 */

class UtopiaCitiesAdapter(private val onClick: (City) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ArrayList<City?>? = null

    fun updateData(list: ArrayList<City>) {
        if (data == null) data = ArrayList()
        else if (data!!.isNotEmpty()) data?.clear()
        data?.addAll(list)
        notifyDataSetChanged()
    }
    fun addData(list: ArrayList<City>) {
        if (data == null) data = ArrayList()
        data?.addAll(list)
        notifyDataSetChanged()
    }

    fun addLoadingView() {
        Handler().post {
            data?.let {
                it.add(null)
                notifyItemInserted(it.size - 1)
            }
        }
    }

    fun removeLoadingView() {
        data?.let {
            if (it.size != 0) {
                it.removeAt(it.size - 1)
                notifyItemRemoved(it.size)
            }
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        data?.let {
            if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
                (holder as UtopiaViewHolder).bindData(it[position]!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == Constant.VIEW_TYPE_ITEM) {
            UtopiaViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_utopia_cities,
                    parent,
                    false
                )
            )
        } else {
            LoadingHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int): Int {
        return if (data != null && data!![position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    inner class UtopiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(data: City) {
            itemView.apply {
                tvCity?.text = data.city ?: ""
                tvCountry?.text = data.country ?: ""
                tvPopulation?.text = formatFloat(data.population)
                setOnClickListener { onClick(data) }
            }
        }

        private fun formatFloat(float: Float): String {
            val decimalFormat = DecimalFormat("##,###,###")
            return decimalFormat.format(float)
        }
    }

    inner class LoadingHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData() {}
    }
}