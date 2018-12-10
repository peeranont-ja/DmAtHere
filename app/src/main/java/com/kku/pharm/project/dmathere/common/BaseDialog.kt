package com.kku.pharm.project.dmathere.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.kku.pharm.project.dmathere.R

object BaseDialog {
    fun createSimpleOkErrorDialog(context: Context?,
                                  message: String): Dialog {

        return createOKCancelDialogWithCallback(context!!,
                "แจ้งเตือน",
                message,
                null,
                "ตกลง",
                null,
                DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() },
                true)
    }

    private fun createOKCancelDialogWithCallback(context: Context,
                                                 title: String,
                                                 message: String,
                                                 negativeText: String?,
                                                 positiveText: String,
                                                 negativeListener: DialogInterface.OnClickListener?,
                                                 positiveListener: DialogInterface.OnClickListener?,
                                                 cancelable: Boolean): Dialog {

        val builder =
                MaterialDialog
                        .Builder(context)
                        .customView(R.layout.dialog_simple, false)
                        .autoDismiss(true)
                        .cancelable(cancelable)

        val materialDialog = builder.build()
        val customView = materialDialog.customView

        val textTitle: TextView = customView!!.findViewById(R.id.tv_spinner_title)

        if (!title.isBlank()) {
            textTitle.text = title
        } else textTitle.visibility = View.GONE

        val textMessage: TextView = customView.findViewById(R.id.text_message)
        if (!message.isBlank()) {
            textMessage.text = message
        } else textMessage.visibility = View.GONE

        val textPositive: TextView = customView.findViewById(R.id.text_positive)
        if (!positiveText.isBlank()) {
            textPositive.text = positiveText
            textPositive.setOnClickListener {
                positiveListener?.onClick(materialDialog, DialogInterface.BUTTON_POSITIVE)
                materialDialog.dismiss()
            }
        } else textPositive.visibility = View.GONE

        val textNegative: TextView = customView.findViewById(R.id.text_negative)
        if (!negativeText.isNullOrBlank()) {
            textNegative.text = negativeText
            textNegative.setOnClickListener {
                negativeListener?.onClick(materialDialog, DialogInterface.BUTTON_NEGATIVE)
                materialDialog.dismiss()
            }
        } else textNegative.visibility = View.GONE

        return materialDialog
    }
}
