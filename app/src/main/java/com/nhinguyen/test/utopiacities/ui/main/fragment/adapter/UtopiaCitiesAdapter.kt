package com.nhinguyen.test.utopiacities.ui.main.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nhinguyen.test.utopiacities.R
import com.nhinguyen.test.utopiacities.model.City
import kotlinx.android.synthetic.main.item_utopia_cities.view.*

/**
 * Created by NhiNguyen on 3/24/2020.
 */

class UtopiaCitiesAdapter(private val data: ArrayList<City>?, private val onClick: (City) -> Unit) :
    RecyclerView.Adapter<UtopiaCitiesAdapter.UtopiaViewHolder>() {
    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: UtopiaViewHolder, position: Int) {
        data?.let {
            if (position < data.size) {
                holder.bindData(data[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtopiaViewHolder =
        UtopiaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_utopia_cities,
                parent,
                false
            )
        )

    inner class UtopiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(data: City) {
            itemView.apply {
                tvCity?.text = data.city ?: ""
                tvCountry?.text = data.country ?: ""
                tvPopulation?.text = "${data.population}"
            }
        }
    }
}