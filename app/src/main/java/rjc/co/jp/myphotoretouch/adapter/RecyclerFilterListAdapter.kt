package rjc.co.jp.myphotoretouch.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.co.cyberagent.android.gpuimage.*
import rjc.co.jp.myphotoretouch.R
import rjc.co.jp.myphotoretouch.executor.FilterExecutor

/**
 * フィルタリストアダプタクラス.
 */
class RecyclerFilterListAdapter(
        context: Context, baseBitmap: Bitmap, listener: OnFilterClickListener) :
        RecyclerView.Adapter<RecyclerFilterListAdapter.ViewHolder>() {

    companion object {
        // フィルタ未選択中番号
        private val NON_SELECTED_ORDER_VALUE = -1
    }

    private val mFilterClickListener = listener
    private val mContext: Context = context
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private val mFilterOrderList: ArrayList<String> = ArrayList()
    private var mBaseBitmap : Bitmap? = null

    init{
        mBaseBitmap = baseBitmap
    }

    interface OnFilterClickListener {
        fun onFilterClick(filterList: ArrayList<String>)
    }

    override fun getItemCount(): Int {
        return FilterExecutor.FILTER_LIST.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(mInflater.inflate(R.layout.item_filter_list, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder == null) {
            return
        }
        // positionでfilterの名を取得
        val filterName = FilterExecutor.FILTER_LIST[position]
        holder.mFilterName.text = filterName

        // 選択番号描画
        if (mFilterOrderList.contains(filterName)) {
            for (i in mFilterOrderList.indices) {
                if (filterName == mFilterOrderList[i]) {
                    holder.mOrderNo = i + 1
                    holder.mOrderNumberText.text = Integer.toString(holder.mOrderNo)
                    break
                }
            }
        } else {
            holder.mOrderNumberText.text = ""
        }

        holder.mFilteredImage.setImageBitmap(null)

        if(mBaseBitmap == null){
            holder.mItemView.setOnClickListener(null)
            return
        }
        val gpuImage = GPUImage(mContext)
        gpuImage.setImage(mBaseBitmap)
        val filterExecutor = FilterExecutor(mContext, mBaseBitmap!!)

        filterExecutor.addGpuFilter(filterName)
        holder.mFilteredImage.setImageBitmap(filterExecutor.getFilteredBitmap())

        holder.mItemView.setOnClickListener({
            if (holder.mOrderNo != NON_SELECTED_ORDER_VALUE) {
                mFilterOrderList.remove(filterName)
                holder.mOrderNo = NON_SELECTED_ORDER_VALUE
            } else {
                mFilterOrderList.add(filterName)
            }
            mFilterClickListener.onFilterClick(mFilterOrderList)
            notifyDataSetChanged()
        })
    }

    public fun changeBaseImageBitmap(bitmap: Bitmap) {
        if (mBaseBitmap != null) {
            mBaseBitmap!!.recycle()
            mBaseBitmap = null
        }
        mBaseBitmap = bitmap
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mFilterName: TextView = itemView.findViewById(R.id.filter_name)
        var mOrderNumberText: TextView = itemView.findViewById(R.id.filtering_number)
        var mFilteredImage: ImageView = itemView.findViewById(R.id.filtered_image)
        var mItemView: View = itemView.findViewById(R.id.area_filter_item)
        var mOrderNo = NON_SELECTED_ORDER_VALUE
    }

}