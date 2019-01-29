package rjc.co.jp.myphotoretouch.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * Created by ninomae makoto.
 * @sample
 * var dialog = ConfirmDialog()
 * dialog.title = "タイトル"
 * dialog.msg = "メッセージ"
 * dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
 *     Log.d( "tag", "ok clicked")
 * }
 * dialog.show( supportFragmentManager, "tag" )
 */
class ConfirmDialog : DialogFragment() {

    var title = "title"
    var msg = "msg"
    // 選択肢
    val dialogMenu = arrayOf<String>("端末に画像を保存", "GoogleDriveへ保存")
    var onRadioClickListener : DialogInterface.OnClickListener? = null
    var okText = "OK"
    var cancelText = "cancel"
    /** ok押下時の挙動 */
    var onOkClickListener : DialogInterface.OnClickListener? = null
    /** cancel押下時の挙動 デフォルトでは何もしない */
    var onCancelClickListener : DialogInterface.OnClickListener? = DialogInterface.OnClickListener { _, _ -> }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the Builder class for convenient dialog construction
        val builder = AlertDialog.Builder(activity)

        builder.setTitle(title)
                .setMessage(msg)
                .setTitle("保存ファイル名")
                .setPositiveButton(okText, onOkClickListener)
                .setNegativeButton(cancelText, onCancelClickListener)
                .setSingleChoiceItems(dialogMenu, 1, onRadioClickListener)

        // Create the AlertDialog object and return it
        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        // onPause でダイアログを閉じる場合
        dismiss()
    }
}