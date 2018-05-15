package com.bignerdranch.android.criminalintent

import java.util.*

/**
 * Created by jcoffman on 3/21/18.
 */
class Crime(var mTitle: String = "", var isSolved: Boolean = false) {
    lateinit var mId: UUID
    lateinit var mDate: Date

    constructor() : this("", false) {
        mId = UUID.randomUUID()
        mDate = Date()
    }

    constructor(id: UUID) : this("", false) {
        mId = id
        mDate = Date()
    }
}