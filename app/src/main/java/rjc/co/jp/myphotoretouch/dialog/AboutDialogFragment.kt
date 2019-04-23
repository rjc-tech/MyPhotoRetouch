package rjc.co.jp.myphotoretouch.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import rjc.co.jp.myphotoretouch.R


/**
 * About画面を表示するためのフラグメントクラスです.
 */
class AboutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rootView = activity.layoutInflater.inflate(R.layout.dialog_about, null)
        val builder = AlertDialog.Builder(activity)
        builder.setCancelable(true).setView(rootView)
        builder.setTitle(R.string.dialog_about_title)

        val ossTextView = rootView.findViewById<TextView>(R.id.oss_licence_text)
        val text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(getString(R.string.oss_licence_01), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(getString(R.string.oss_licence_01))
        }

        ossTextView.text = text

        return builder.create()
    }
}