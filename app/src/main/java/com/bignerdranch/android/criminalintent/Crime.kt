package com.bignerdranch.android.criminalintent

import java.util.*

/**
 * Created by jcoffman on 3/21/18.
 */
class Crime(var mTitle: String = "", var isSolved: Boolean = false) {
    val mId: UUID = UUID.randomUUID()
    val mDate: Date = Date()
}