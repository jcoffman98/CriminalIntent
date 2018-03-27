package com.bignerdranch.android.criminalintent

import java.util.*

/**
 * Created by jcoffman on 3/21/18.
 */
object CrimeLab {
    private val mCrimes : ArrayList<Crime> = ArrayList()

    init {
        for (i in 1..100) {
            val crime = Crime("Crime #$i", i%2 == 0)
            mCrimes.add(crime)
        }
    }

    fun getCrimes(): ArrayList<Crime> {
        return mCrimes
    }

    fun getCrime(id: UUID): Crime? {
        return mCrimes.find { c: Crime -> c.mId == id}
    }
}