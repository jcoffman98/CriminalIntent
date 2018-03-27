package com.bignerdranch.android.criminalintent

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*

/**
 * Created by jcoffman on 3/21/18.
 */
class CrimeListFragment : Fragment() {
    private lateinit var mCrimeRecyclerView: RecyclerView
    private var mAdapter : CrimeAdapter? = null
    private var mSelected  = 0

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_crime_list, container, false)
        mCrimeRecyclerView = v?.findViewById(R.id.crime_recycler_view) as RecyclerView
        mCrimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        updateUI()
        return v
    }

    private fun updateUI(): Unit {
        val crimes = CrimeLab.getCrimes()

        if(mAdapter == null) {
            mAdapter = CrimeAdapter(crimes)
            mCrimeRecyclerView.adapter = mAdapter
        } else {
            //mAdapter!!.notifyDataSetChanged()
            Log.v("LOGGER", "Position $mSelected")
            mAdapter!!.notifyItemChanged(mSelected)
        }
        Log.v("TAGGER", "${crimes.size}")
    }

    private inner class CrimeHolder(inflator: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflator.inflate(R.layout.list_item_crime, parent, false)), View.OnClickListener {
        private var mTitleTextView: TextView = itemView.findViewById(R.id.crime_title) as TextView
        private var mDateTextView: TextView = itemView.findViewById(R.id.crime_date) as TextView
        private  var mSolvedImageView: ImageView = itemView.findViewById(R.id.crime_solved) as ImageView
        private lateinit var mCrime: Crime

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mSelected = adapterPosition
            val intent = CrimeActivity.newIntent(activity, mCrime.mId)
            startActivity(intent)
        }

        fun bind(crime: Crime) : Unit {
            mCrime = crime
            mTitleTextView.setText(mCrime.mTitle)
            mDateTextView.text = java.text.SimpleDateFormat("EEE, MMM d, YYYY").format(mCrime.mDate)
            mSolvedImageView.visibility = when(crime.isSolved) {
                false -> View.GONE
                true -> View.VISIBLE
            }
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
            val h = holder as RecyclerView.ViewHolder
            Log.v("POSITION", "Item at ${holder?.adapterPosition}")
            val crime = mCrimes[position]
            holder?.bind(crime)
        }
    }
}

