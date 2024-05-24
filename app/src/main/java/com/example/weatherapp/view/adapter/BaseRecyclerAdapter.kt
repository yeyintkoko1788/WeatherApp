package com.example.weatherapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.view.viewholder.BaseViewHolder
import java.util.*

abstract class
BaseRecyclerAdapter<itemType, viewType : BaseViewHolder<itemType>>(private var context: Context) :
    RecyclerView.Adapter<viewType>() {

    val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }
    protected var mData: MutableList<itemType>? = null
    protected var updatedIndex: Int = -1

    val items: List<itemType>
        get() = if (mData == null) ArrayList() else mData!!

    init {
        mData = ArrayList()
    }

    override fun onBindViewHolder(holder: viewType, position: Int) {
        if (mData!!.isNotEmpty()) {
            holder.setData(mData!![position])
        }
    }

    override fun getItemCount(): Int {

        return mData!!.size
    }

    fun getItemAt(position: Int): itemType? {
        return if (position < mData!!.size - 1) mData!![position] else null
    }

    fun addNewData(newItem: itemType, position: Int) {
        if (mData != null) {
            mData!!.add(position, newItem)
            notifyDataSetChanged()
        }
    }

    fun update(newDataList: List<itemType>) {
        val diffResult = DiffUtil.calculateDiff(DiffUtils(mData!!, newDataList))
        this.mData!!.clear()
        this.mData!!.addAll(newDataList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun appendNewData(newData: List<itemType>) {
        if (mData!!.isEmpty()) {
            mData!!.addAll(newData)
            notifyDataSetChanged()
        } else
            update(newData)
    }

    fun removeData(data: itemType) {
        mData!!.remove(data)
        notifyDataSetChanged()
    }

    fun addNewData(data: itemType) {
        mData!!.add(data)
        notifyDataSetChanged()
    }

    fun addNewDataList(dataList: List<itemType>) {
        mData!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setNewDataList(dataList: List<itemType>) {
        mData!!.clear()
        mData!!.addAll(dataList)
        notifyDataSetChanged()
    }

    fun clearData() {
        mData?.clear()
        notifyDataSetChanged()
    }

    fun updateItemAt(index: Int, item: itemType) {
        mData!![index] = item
        notifyItemChanged(index)
    }
}
