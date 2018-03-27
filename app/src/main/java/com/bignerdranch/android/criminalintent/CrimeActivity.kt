package com.bignerdranch.android.criminalintent

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId)
    }

    companion object {
        val EXTRA_CRIME_ID = "com.bignerdranch.android.crimnalintent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID) : Intent {
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }
}
