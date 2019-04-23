package rjc.co.jp.myphotoretouch.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import rjc.co.jp.myphotoretouch.R
import android.content.Intent
import android.provider.MediaStore
import android.content.ContentValues
import android.app.Activity
import android.widget.ImageView
import android.net.Uri


class MainActivity : AppCompatActivity() {

    var mPictureUri: Uri? = null
    var IMAGE_CHOOSER_RESULTCODE = 1000

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
            R.id.save_image ->{}
            R.id.load_image ->{
                launchChooser()
            }
            R.id.about ->{}
        }
        return false
    }

    /**
     * 画像読み込みチューザーを表示します.
     */
    private fun launchChooser() {
        // ギャラリーから選択
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)

        // カメラで撮影
        val filename = System.currentTimeMillis().toString() + ".jpg"
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        mPictureUri = contentResolver
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPictureUri)

        // ギャラリー選択のIntentでcreateChooser()
        val chooserIntent = Intent.createChooser(galleryIntent, "Pick Image")
        // EXTRA_INITIAL_INTENTS にカメラ撮影のIntentを追加
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        startActivityForResult(chooserIntent, IMAGE_CHOOSER_RESULTCODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_CHOOSER_RESULTCODE) {

            if (resultCode != Activity.RESULT_OK) {
                if (mPictureUri != null) {
                    contentResolver.delete(mPictureUri, null, null)
                    mPictureUri = null
                }
                return
            }
            // 画像を取得
            val result = if (data == null) mPictureUri else {
                data.data
            }
            val iv = findViewById(R.id.main_image) as ImageView
            iv.setImageURI(result)

            mPictureUri = null
        }
    }
}
