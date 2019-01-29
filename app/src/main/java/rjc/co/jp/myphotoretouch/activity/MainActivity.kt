package rjc.co.jp.myphotoretouch.activity

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import rjc.co.jp.myphotoretouch.R
import rjc.co.jp.myphotoretouch.adapter.RecyclerFilterListAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.filter_list)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = linearLayoutManager

        val imageView =  findViewById<ImageView>(R.id.main_image)
        val baseImage = resources.getDrawable(R.drawable.sample, null) as BitmapDrawable
        recyclerView.adapter = RecyclerFilterListAdapter(applicationContext, baseImage.bitmap )

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_image ->{}
            R.id.load_image ->{}
            R.id.about ->{}
        }

        return false
    }
}

