package com.bignerdranch.android.criminalintent

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
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
    private var mSelected  = -1
    private var mSubtitleVisible = false

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu?.findItem(R.id.show_subtitle)
        if(mSubtitleVisible) {
            subtitleItem?.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem?.setTitle(R.string.show_subtitle)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_crime_list, container, false)
        mCrimeRecyclerView = v?.findViewById(R.id.crime_recycler_view) as RecyclerView
        mCrimeRecyclerView.layoutManager = LinearLayoutManager(activity)

        try {
            mSubtitleVisible = savedInstanceState!!.getBoolean(SAVED_SUBTITLE_VISIBLE)
        } catch (e: RuntimeException) {

        }

        updateUI()
        return v
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                CrimeLab.getInstance(context).addCrime(crime)
                val intent =  CrimePagerActivity.newIntent(activity, crime.mId)
                startActivity(intent)
                return true
            }
            R.id.show_subtitle -> {
                mSubtitleVisible = !mSubtitleVisible
                activity.invalidateOptionsMenu()
                updateSubtitle()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible)
    }
    private fun updateSubtitle() {
        val crimeCount = CrimeLab.getInstance(context).getCrimes().size
        var subtitle = getString(R.string.subtitle_format, crimeCount)

        if(!mSubtitleVisible) {
            subtitle = null
        }
        val act = activity as AppCompatActivity
        act.supportActionBar?.subtitle = subtitle
    }

    private fun updateUI() {
        val crimes = CrimeLab.getInstance(context).getCrimes()

        if(mAdapter == null) {
            mAdapter = CrimeAdapter(crimes)
            mCrimeRecyclerView.adapter = mAdapter
        } else {
            Log.v("LOGGER", "Position $mSelected")
            mAdapter!!.setCrimes(crimes)
            mAdapter!!.notifyItemChanged(mSelected)
        }
        updateSubtitle()
        Log.v("TAGGER", "${crimes.size}")
    }

    companion object {
        private const val SAVED_SUBTITLE_VISIBLE = "subtitle"
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
            Log.v("HOLDER", "crime id = ${mCrime.mId}")
            val intent = CrimePagerActivity.newIntent(activity, mCrime.mId)
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

    private inner class CrimeAdapter(var mCrimes: ArrayList<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CrimeHolder {
            Log.v("TAGGAROO", "creating view holder")
            val l = LayoutInflater.from(activity)
            return CrimeHolder(l, parent!!)
        }

        override fun getItemCount(): Int {
            return mCrimes.size
        }

        fun setCrimes(crimes: ArrayList<Crime>) {
            mCrimes = crimes
        }

        override fun onBindViewHolder(holder: CrimeHolder?, position: Int) {
            val h = holder as RecyclerView.ViewHolder
            Log.v("POSITION", "Item at ${holder?.adapterPosition}")
            val crime = mCrimes[position]
            holder?.bind(crime)
        }
    }
}

