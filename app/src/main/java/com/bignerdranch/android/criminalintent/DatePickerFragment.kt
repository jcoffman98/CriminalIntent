package com.bignerdranch.android.criminalintent

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater

class DatePickerFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)
        val builder = android.support.v7.app.AlertDialog.Builder(activity)
        return builder.setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok, null).create()
    }
}