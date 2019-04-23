package rjc.co.jp.myphotoretouch.executor

import android.content.Context
import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.*

/**
 * フィルタ適用処理クラス.
 */
class FilterExecutor(context: Context) {

    companion object {
        val FILTER_LIST = arrayListOf("セピア", "擬色", "半透明", "黒こげ", "スケッチフィルタ", "黒画", "色反転", "ガラス玉", "ぼかす", "渦巻き")
    }
    private val mGpuImage: GPUImage = GPUImage(context)
    private var mBaseImage: Bitmap? = null


    constructor(context: Context, baseBitmap: Bitmap) : this(context){
        setBaseImage(baseBitmap)
    }

    /**
     * 画像にフィルタを適用します.
     */
    fun addGpuFilter(filterName: String) {
        if (mBaseImage == null) {
            return
        }
        when (filterName) {
            "セピア" -> {
                mGpuImage.setFilter(GPUImageSepiaFilter())
            }
            "擬色" -> {
                mGpuImage.setFilter(GPUImageFalseColorFilter())
            }
            "半透明" -> {
                mGpuImage.setFilter(GPUImageOpacityFilter(0.5f))
            }
            "黒こげ" -> {
                mGpuImage.setFilter(GPUImageGammaFilter(5.0f))
            }
            "ライン" -> {
                mGpuImage.setFilter(GPUImageSketchFilter())
            }
            "黒画" -> {
                mGpuImage.setFilter(GPUImageSobelEdgeDetection())
            }
            "ガラス玉" -> {
                mGpuImage.setFilter(GPUImageGlassSphereFilter())
            }
            "色反転" -> {
                mGpuImage.setFilter(GPUImageColorInvertFilter())
            }
            "ぼかす" -> {
                mGpuImage.setFilter(GPUImageCrosshatchFilter())
            }
            "渦巻き" -> {
                mGpuImage.setFilter(GPUImageSwirlFilter())
            }
            else -> {
                // ignore
            }
        }
    }

    fun setBaseImage(bitmap : Bitmap){
        mBaseImage = bitmap
        mGpuImage.setImage(mBaseImage)
    }

    /**
     * フィルタ適用後の画像を返します.
     */
    fun getFilteredBitmap(): Bitmap {
        return mGpuImage.bitmapWithFilterApplied
    }


}


