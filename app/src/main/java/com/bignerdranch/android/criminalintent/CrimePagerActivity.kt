package com.bignerdranch.android.criminalintent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import java.util.*

class CrimePagerActivity : AppCompatActivity() {
    private lateinit var mViewPager: ViewPager
    private lateinit var mCrimes: List<Crime>
    private lateinit var mFirstButton: Button
    private lateinit var mLastButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        Log.v("CRIME PAGER", "crime id = $crimeId")

        mCrimes = CrimeLab.getCrimes()

        mViewPager = findViewById(R.id.crime_view_pager) as ViewPager
        mViewPager.adapter =  object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = mCrimes.get(position)
                return CrimeFragment.newInstance(crime.mId)
            }

            override fun getCount(): Int {
                return mCrimes.size
            }
        }

        mFirstButton = findViewById(R.id.jump_first_button) as Button
        mFirstButton.setOnClickListener {
            mViewPager.currentItem = 0
        }
        mLastButton = findViewById(R.id.jump_last_button) as Button
        mLastButton.setOnClickListener {
            mViewPager.currentItem = mCrimes.size-1
        }

        for(i in 0 until mCrimes.size) {
            Log.v("BOO", "$i")
            if(mCrimes[i].mId.equals(crimeId)) {
                mViewPager.currentItem = i
                break
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