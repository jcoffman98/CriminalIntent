package com.bignerdranch.android.criminalintent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import java.util.*

class CrimePagerActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager
    private lateinit var mCrimes: List<Crime>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        mViewPager = findViewById(R.id.crime_view_pager) as ViewPager
        mCrimes = CrimeLab.getCrimes()

        mViewPager.adapter =  object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = mCrimes.get(position)
                return CrimeFragment.newInstance(crime.mId)
            }

            override fun getCount(): Int {
                return mCrimes.size
            }
        }
    }

    companion object {
        private val EXTRA_CRIME_ID: String = "com.bignerdranch.android.criminalintent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID) : Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }
}