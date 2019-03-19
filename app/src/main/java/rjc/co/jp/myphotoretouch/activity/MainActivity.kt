package rjc.co.jp.myphotoretouch.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import rjc.co.jp.myphotoretouch.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            R.id.save_image ->{
                // ダイアログを表示する
                var dialog = ConfirmDialog()
                dialog.show(supportFragmentManager, "tag")
            }
            R.id.load_image ->{}
            R.id.about ->{
                // about画面を表示する
            }
        }
        return false
    }
}

