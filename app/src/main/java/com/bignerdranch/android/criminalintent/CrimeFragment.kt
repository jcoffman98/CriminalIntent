package com.bignerdranch.android.criminalintent

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
    private lateinit var mDetailsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments.getSerializable(ARG_CRIME_ID) as UUID
        mCrime = CrimeLab.getCrime(crimeId)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_crime, container, false)

        mTitleField = v?.findViewById(R.id.crime_title) as EditText
        mTitleField.setText(mCrime.mTitle)
        mTitleField.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCrime.mTitle = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        mDetailsButton = v?.findViewById(R.id.crime_date) as Button
        mDetailsButton.text = mCrime.mDate.toString()

        mSolvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        mSolvedCheckBox.isChecked = mCrime.isSolved
        mSolvedCheckBox.setOnCheckedChangeListener { _, p1 -> mCrime.isSolved = p1 }
        return v
    }

    companion object {
        private val ARG_CRIME_ID: String = "crime_id"

        fun newInstance(crimeId: UUID) : CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}