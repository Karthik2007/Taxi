package com.karthik.taxi.ui.taxilist

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karthik.taxi.R
import com.karthik.taxi.databinding.ListItemTaxiBinding
import com.karthik.taxi.listener.OnItemClickListener
import com.karthik.taxi.model.Poi
import kotlinx.android.synthetic.main.list_item_taxi.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


/**
 * created by Karthik A on 18/12/18
 */
class TaxiListAdapter( private val listener: OnItemClickListener ): androidx.recyclerview.widget.RecyclerView.Adapter<TaxiListAdapter.TaxiListViewHolder>() {


    private var poiList: List<Poi>? = null

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): TaxiListViewHolder {
        var inflater = LayoutInflater.from(parent.context)

        val binding: ListItemTaxiBinding = DataBindingUtil.inflate(inflater!!, R.layout.list_item_taxi, parent, false)

        return TaxiListViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return poiList?.size ?: 0
    }

    override fun onBindViewHolder(holder: TaxiListViewHolder, pos: Int) {
        holder.binding.poiDetail = poiList!![pos]


        val bottomSheet: BottomSheetDialogFragment = BottomFragment()
        val ac = listener as Fragment
        val frag = ac.childFragmentManager

        holder.binding.root.taxi_list_card.onClick {
            bottomSheet.show(frag,"test")

            frag.executePendingTransactions()
            val view = bottomSheet.view

            println("transaction$view")

            listener.onItemClick(pos, poiList!![pos])
        }

    }

    fun submitList(data: List<Poi>?)
    {
        poiList = data

        notifyDataSetChanged()
    }

    inner class TaxiListViewHolder(var binding: ListItemTaxiBinding): androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root){

        init {

            binding.root.taxi_list_card.onClick {
                listener.onItemClick(adapterPosition, poiList!![adapterPosition])
            }

        }
    }
}