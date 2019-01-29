package rjc.co.jp.myphotoretouch.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.co.cyberagent.android.gpuimage.*
import rjc.co.jp.myphotoretouch.R


/**
 * Created by h_miyamoto on 2019/01/29.
 */

class RecyclerFilterListAdapter(context: Context, baseImage: Bitmap) : RecyclerView.Adapter<RecyclerFilterListAdapter.ViewHolder>() {

    private var mInflater: LayoutInflater? = null
    private var mContext: Context? = null
    private val FILTER_LIST = arrayListOf("セピア", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet")
    private var mFilterOrderMap: HashMap<String, Int>? = null
    private var mFilterOrderList: ArrayList<String>? = null
    private var mBaseImage: Bitmap? = null

    init {
        mContext = context
        mInflater = LayoutInflater.from(mContext)
        mFilterOrderMap = HashMap()
        mFilterOrderList = ArrayList()
        mBaseImage = baseImage
    }

    interface OnFilterClickListener{
        fun onFilterClick(filterList : ArrayList<String>)
    }

    override fun getItemCount(): Int {
        return FILTER_LIST.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(mInflater!!.inflate(R.layout.item_filter_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        // positionでfilterの名を取得
        val filterName = FILTER_LIST[position]
        // 選択番号
        val order = mFilterOrderMap!![filterName]

        if (order != null) {
            holder?.mOrderNumber?.text = Integer.toString(order)
        } else {
            holder?.mOrderNumber?.text = Integer.toString(position)
        }

        holder?.mFilterName?.text = filterName

        val gpuImage = GPUImage(mContext)
        gpuImage.setImage(mBaseImage)
        when (filterName) {
            "セピア" -> {
                gpuImage.setFilter(GPUImageSepiaFilter())
                holder?.mFilteredImage?.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "Bravo" -> {
                gpuImage.setFilter(GPUImageFalseColorFilter())
                holder?.mFilteredImage?.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            else -> {
                holder?.mFilteredImage?.setImageBitmap(mBaseImage)
            }
        }

        holder?.mItemView?.setOnClickListener({
            if(order != null){
                mFilterOrderList?.remove(FILTER_LIST[position])
            } else {
                mFilterOrderList?.add(FILTER_LIST[position])
            }
        })

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mFilterName: TextView? = null
        var mOrderNumber: TextView? = null
        var mFilteredImage: ImageView? = null
        var mItemView : View ? = null

        init {
            mFilterName = itemView.findViewById(R.id.filter_name)
            mOrderNumber = itemView.findViewById(R.id.filtering_number)
            mFilteredImage = itemView.findViewById(R.id.filtered_image)
            mItemView = itemView
        }
    }

}