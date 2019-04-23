package rjc.co.jp.myphotoretouch.activity

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import rjc.co.jp.myphotoretouch.R
import rjc.co.jp.myphotoretouch.adapter.RecyclerFilterListAdapter
import rjc.co.jp.myphotoretouch.executor.FilterExecutor

class MainActivity : AppCompatActivity(), RecyclerFilterListAdapter.OnFilterClickListener {
    private var mMainImage: ImageView? = null
    private var mBaseBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.filter_list)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager

        mMainImage = findViewById(R.id.main_image)
        val image = resources.getDrawable(R.drawable.sample, null) as BitmapDrawable
        mBaseBitmap = image.bitmap
        recyclerView.adapter = RecyclerFilterListAdapter(applicationContext, image.bitmap, this)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option, menu)
        return true
    }

    /**
     * フィルタリストクリック時に呼ばれます.
     */
    override fun onFilterClick(filterList: ArrayList<String>) {
        if (filterList.isEmpty()) {
            mMainImage?.setImageBitmap(mBaseBitmap)
            return
        }

        // 選択中フィルタを順番に適用
        val filterExecutor = FilterExecutor(applicationContext, mBaseBitmap!!)
        for (filterName in filterList) {
            filterExecutor.addGpuFilter(filterName)
        }
        mMainImage?.setImageBitmap(filterExecutor.getFilteredBitmap())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_image -> {
            }
            R.id.load_image -> {
            }
            R.id.about -> {
            }
        }

        return false
    }
}

