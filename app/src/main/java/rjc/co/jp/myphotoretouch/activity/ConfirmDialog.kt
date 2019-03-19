package rjc.co.jp.myphotoretouch.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.*
import rjc.co.jp.myphotoretouch.R
import android.widget.RadioButton

class ConfirmDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // アラートダイアログ設定
        val builder = AlertDialog.Builder(activity)

        // カスタムビュー設定
        val view = activity.layoutInflater.inflate(R.layout.custom_layout, null)

        // アラーダイアログ作成
        builder.setView(view)

        val positiveButton = view.findViewById(R.id.positive_button) as Button
        positiveButton.setOnClickListener(
                View.OnClickListener {
                    // 保存ファイル名設定
                    val editText = view.findViewById(R.id.editText) as EditText

                    if(editText == null || editText.getText().toString() == ""){
                        Toast.makeText(context, "ファイルの名前を入力してください", Toast.LENGTH_SHORT).show()
                    } else {
                        /** 下記はgoogleドライブ拡張用
                        // 保存場所設定
                        val radioGroup  = view.findViewById(R.id.radioGroup) as RadioGroup
                        val id = radioGroup.getCheckedRadioButtonId()
                        val radioButton = view.findViewById(id) as RadioButton
                        // 保存できたか表示
                        Toast.makeText(context, radioButton.getText().toString(), Toast.LENGTH_SHORT).show()
                        **/
                        Toast.makeText(context, "端末に保存しました", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
        )

        return builder.create()
    }

    override fun onPause() {
        super.onPause()
        // onPause でダイアログを閉じる場合
        dismiss()
    }
}