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
class RecyclerFilterListAdapter(context: Context, baseBitmap: Bitmap, listener: OnFilterClickListener) : RecyclerView.Adapter<RecyclerFilterListAdapter.ViewHolder>() {

    private val NON_SELECTED_ORDER_VALUE = -1

    private val mFilterClickListener = listener
    private val mContext: Context = context
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private val mFilterOrderList: ArrayList<String> = ArrayList()
    private val mBaseBitmap: Bitmap = baseBitmap

    val FILTER_LIST = arrayListOf("セピア", "擬色", "半透明", "黒こげ", "スケッチフィルタ", "黒画", "色反転", "ガラス玉", "ぼかす", "渦巻き")

    interface OnFilterClickListener {
        fun onFilterClick(filterList: ArrayList<String>)
    }

    override fun getItemCount(): Int {
        return FILTER_LIST.size
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
        val filterName = FILTER_LIST[position]
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

        val gpuImage = GPUImage(mContext)
        gpuImage.setImage(mBaseBitmap)
        val filterExecutor = FilterExecutor(mContext, mBaseBitmap)
        when (filterName) {

            "セピア" -> {
                gpuImage.setFilter(GPUImageSepiaFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "擬色" -> {
                gpuImage.setFilter(GPUImageFalseColorFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "半透明" -> {
                gpuImage.setFilter(GPUImageOpacityFilter(0.5f))
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "黒こげ" -> {
                gpuImage.setFilter(GPUImageGammaFilter(5.0f))
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "スケッチフィルタ" -> {
                gpuImage.setFilter(GPUImageSketchFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "黒画" -> {
                gpuImage.setFilter(GPUImageSobelEdgeDetection())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }

            "ガラス玉" -> {
                gpuImage.setFilter(GPUImageGlassSphereFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "色反転" -> {
                gpuImage.setFilter(GPUImageColorInvertFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "ぼかす" -> {
                gpuImage.setFilter(GPUImageCrosshatchFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            "渦巻き" -> {
                gpuImage.setFilter(GPUImageSwirlFilter())
                holder.mFilteredImage.setImageBitmap(gpuImage.bitmapWithFilterApplied)
            }
            else -> {
                holder.mFilteredImage.setImageBitmap(mBaseBitmap)
            }
        }

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mFilterName: TextView = itemView.findViewById(R.id.filter_name)
        var mOrderNumberText: TextView = itemView.findViewById(R.id.filtering_number)
        var mFilteredImage: ImageView = itemView.findViewById(R.id.filtered_image)
        var mItemView: View = itemView.findViewById(R.id.area_filter_item)
        var mOrderNo = NON_SELECTED_ORDER_VALUE
    }

}