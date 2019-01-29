package rjc.co.jp.myphotoretouch.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import rjc.co.jp.myphotoretouch.R
import android.content.DialogInterface;


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
                // どの選択肢が選ばれたかを保持する変数
                var selectedId = 0
                // ダイアログを表示する
                var dialog = ConfirmDialog()
                dialog.title = "保存ファイル名"
                dialog.msg = "参照ファイル"
                dialog.onRadioClickListener = DialogInterface.OnClickListener{dialog, id ->
                    selectedId = id
                }
                dialog.okText = "OK"
                dialog.cancelText = "キャンセル"
                dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
                }
                dialog.onCancelClickListener = DialogInterface.OnClickListener { dialog, id ->
                }
                // supportFragmentManagerはAppCompatActivity(正確にはFragmentActivity)を継承したアクティビティで使用可
                dialog.show(supportFragmentManager, "tag")
            }
            R.id.load_image ->{}
            R.id.about ->{}
        }
        return false
    }
}

