package com.suifeng.sdk.permission.widget

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.suifeng.sdk.permission.R

internal class AppSettingsDialog(
    private val message: String = "",
    private val positive: (() -> Unit) = {},
    private val negative: (() -> Unit) = {}
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(message)
                .setPositiveButton(R.string.permission_setting) { _, _ ->
                    positive()
                }
                .setNegativeButton(R.string.permission_cancel) { _, _ ->
                    dismissAllowingStateLoss()
                    negative()
                }
        return builder.create()
    }
}