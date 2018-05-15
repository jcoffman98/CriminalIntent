package com.bignerdranch.android.criminalintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.support.v4.app.Fragment
import android.widget.Button
import java.util.*

/**
 * Created by jcoffman on 3/21/18.
 */
class CrimeFragment : Fragment() {
    private lateinit var mCrime: Crime
    private lateinit var mTitleField: EditText
    private lateinit var mSolvedCheckBox: CheckBox
    private lateinit var mDateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments.getSerializable(ARG_CRIME_ID) as UUID
        mCrime = CrimeLab.getInstance(context).getCrime(crimeId)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_crime, container, false)

        mTitleField = v?.findViewById(R.id.crime_title) as EditText
        mTitleField.setText(mCrime.mTitle)
        mTitleField.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
              return
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCrime.mTitle = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                return
            }
        })

        mDateButton = v?.findViewById(R.id.crime_date) as Button
        updateDate()
        mDateButton.setOnClickListener( object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dialog = DatePickerFragment.newInstance(mCrime.mDate)
                dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                dialog.show(fragmentManager, DIALOG_DATE)
            }
        })

        mSolvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        mSolvedCheckBox.isChecked = mCrime.isSolved
        mSolvedCheckBox.setOnCheckedChangeListener { _, p1 -> mCrime.isSolved = p1 }
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK)
            return

        if(requestCode == REQUEST_DATE) {
            val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mCrime.mDate = date
            updateDate()
        }
    }

    override fun onPause() {
        super.onPause()
        CrimeLab.getInstance(activity).updateCrime(mCrime)
    }
    private fun updateDate() {
        mDateButton.text = mCrime.mDate.toString()
    }

    companion object {
        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "DialogDate"
        private const  val REQUEST_DATE = 0

        fun newInstance(crimeId: UUID) : CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}