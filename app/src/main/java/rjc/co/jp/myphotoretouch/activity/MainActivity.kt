package rjc.co.jp.myphotoretouch.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import rjc.co.jp.myphotoretouch.R
import rjc.co.jp.myphotoretouch.adapter.RecyclerFilterListAdapter
import rjc.co.jp.myphotoretouch.dialog.AboutDialogFragment
import rjc.co.jp.myphotoretouch.executor.FilterExecutor
import java.io.FileNotFoundException
import java.io.OutputStream


class MainActivity : AppCompatActivity(), RecyclerFilterListAdapter.OnFilterClickListener {

    private var mMainImageView: ImageView? = null
    private var mBaseBitmap: Bitmap? = null
    private var mFilteredBitmap: Bitmap? = null
    private var mRecyclerView: RecyclerView? = null

    private var mPictureUri: Uri? = null

    companion object {
        val IMAGE_CHOOSER_RESULT_CODE = 1000
        val REQUEST_PERMISSION = 1001
        val REQUEST_SAVE_IMAGE = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.filter_list)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRecyclerView?.layoutManager = linearLayoutManager

        mMainImageView = findViewById(R.id.main_image)
        mBaseBitmap = BitmapFactory.decodeResource(resources, R.drawable.sample_image2)
        mMainImageView?.setImageBitmap(mBaseBitmap)
        mRecyclerView?.adapter = RecyclerFilterListAdapter(applicationContext, mBaseBitmap!!, this)

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
            mMainImageView?.setImageBitmap(mBaseBitmap)
            mFilteredBitmap = mBaseBitmap
            return
        }

        // 選択中フィルタを順番に適用
        val filterExecutor = FilterExecutor(applicationContext, mBaseBitmap!!)
        for (filterName in filterList) {
            filterExecutor.addGpuFilter(filterName)
            filterExecutor.setBaseImage(filterExecutor.getFilteredBitmap())
        }
        mFilteredBitmap = filterExecutor.getFilteredBitmap()
        mMainImageView?.setImageBitmap(mFilteredBitmap)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_image -> {
                saveBitmap()
            }
            R.id.load_image -> {
                launchChooser()
            }
            R.id.about -> {
                showAboutDialog()
            }
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

        startActivityForResult(chooserIntent, IMAGE_CHOOSER_RESULT_CODE)
    }

    private fun showAboutDialog(){
        val dialog = AboutDialogFragment()
        dialog.show(fragmentManager, "")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_CHOOSER_RESULT_CODE) {

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
            mMainImageView?.setImageURI(result)
            mBaseBitmap = MediaStore.Images.Media.getBitmap(contentResolver, result)
            mPictureUri = null

            (mRecyclerView?.adapter as RecyclerFilterListAdapter)
                    .changeBaseImageBitmap(mBaseBitmap!!)
        } else if (requestCode == REQUEST_SAVE_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data?.data != null) {
                val uri = data.data
                var os: OutputStream? = null
                try {
                    os = contentResolver.openOutputStream(uri)
                    mFilteredBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, os)
                } catch (e: FileNotFoundException) {
                    Log.e("Error", e.message)
                } finally {
                    os?.close()
                }
            }


        }
    }

    private fun saveBitmap() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_TITLE, "img.png")
        startActivityForResult(intent, REQUEST_SAVE_IMAGE)
    }

    private fun checkPermission(): Boolean {
        return (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION) {
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "ストレージからの読み込み許可がありません", Toast.LENGTH_SHORT).show()
                    finish()
                    return
                }
            }
        }
    }
}
