package com.bignerdranch.android.criminalintent

import android.support.v4.app.Fragment

/**
 * Created by jcoffman on 3/21/18.
 */
class CrimeListActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }

}