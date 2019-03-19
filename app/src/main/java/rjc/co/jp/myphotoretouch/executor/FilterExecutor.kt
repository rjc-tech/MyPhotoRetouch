package rjc.co.jp.myphotoretouch.executor

import android.content.Context
import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.*

class FilterExecutor(context: Context, baseBitmap: Bitmap) {

    private val mGpuImage: GPUImage = GPUImage(context)
    private val mBaseImage: Bitmap = baseBitmap

    init {
        mGpuImage.setImage(mBaseImage)
    }

    fun addGpuFilter(filterName: String) {
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
                //  gpuImage.setFilter(GPUImageSphereRefractionFilter()) 上の上下反転版
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
            }
        }
    }

    fun getFilteredBitmap(): Bitmap {
        return mGpuImage.bitmapWithFilterApplied
    }

}
