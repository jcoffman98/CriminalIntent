package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

/**
 * Created by jcoffman on 3/21/18.
 */
class CrimeListFragment : Fragment() {
    private lateinit var mCrimeRecyclerView: RecyclerView
    private lateinit var mAdapter: CrimeAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_crime_list, container, false)
        mCrimeRecyclerView = v?.findViewById(R.id.crime_recycler_view) as RecyclerView
        mCrimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return v
    }

    private fun updateUI(): Unit {
        val crimes = CrimeLab.getCrimes()
        Log.v("TAGGER", "${crimes.size}")
        mAdapter = CrimeAdapter(crimes)
        mCrimeRecyclerView.adapter = mAdapter
    }

    private inner class CrimeHolder(inflator: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflator.inflate(R.layout.list_item_crime, parent, false)), View.OnClickListener {
        override fun onClick(v: View?) {
            Toast.makeText(activity, mCrime.mTitle + " clicked!", Toast.LENGTH_SHORT).show()
        }

        private var mTitleTextView: TextView = itemView.findViewById(R.id.crime_title) as TextView
        private var mDateTextView: TextView = itemView.findViewById(R.id.crime_date) as TextView
        private lateinit var mCrime: Crime

        fun bind(crime: Crime) : Unit {
            mCrime = crime
            mTitleTextView.setText(mCrime.mTitle)
            mDateTextView.setText(mCrime.mDate.toString())
            Log.v("BINDER", "${mCrime.mTitle}, ${mCrime.mDate}")
        }
    }

    private inner class CrimeAdapter(val mCrimes: ArrayList<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CrimeHolder {
            Log.v("TAGGAROO", "creating view holder")
            val l = LayoutInflater.from(activity)
            return CrimeHolder(l, parent!!)
        }

        override fun getItemCount(): Int {
            return mCrimes.size
        }

        override fun onBindViewHolder(holder: CrimeHolder?, position: Int) {
            val crime = mCrimes[position]
            holder?.bind(crime)
        }
    }
}
