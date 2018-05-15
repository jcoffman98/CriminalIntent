package com.bignerdranch.android.criminalintent

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import database.CrimeBaseHelper
import database.CrimeCursorWrapper
import database.CrimeDbSchema
import java.util.*

/**
 * Created by jcoffman on 3/21/18.
 */
class CrimeLab private constructor(context: Context) {
    private lateinit var mContext: Context
    private lateinit var mDatabase: SQLiteDatabase
    init {
        mContext = context.applicationContext
        mDatabase = CrimeBaseHelper(mContext).writableDatabase
    }

    fun getCrimes(): ArrayList<Crime> {
        val crimes  = arrayListOf<Crime>()
        val cursor = queryCrimes(null, null)
        try {
            cursor.moveToFirst()
            while(!cursor.isAfterLast) {
                crimes.add(cursor.getCrime()!!)
                cursor.moveToNext()
            }
        } finally {
            cursor.close()
        }
        return crimes
    }

    fun getCrime(id: UUID): Crime? {
        val cursor = queryCrimes(CrimeDbSchema.UUID + " = ?", arrayOf(id.toString()))
        try {
            if(cursor.count == 0) {
                return null
            }

            cursor.moveToFirst()
            return cursor.getCrime()
        } finally {
            cursor.close()
        }
    }

    fun addCrime(c: Crime) {
        val values = getContentValues(c)
        mDatabase.insert(CrimeDbSchema.NAME, null, values)
    }

    fun updateCrime(crime: Crime) {
        val uuidstring = crime.mId.toString()
        val values = getContentValues(crime)
        mDatabase.update(CrimeDbSchema.NAME, values, CrimeDbSchema.UUID + " = ?",
                         arrayOf(uuidstring))
    }

    fun getContentValues(crime: Crime) : ContentValues {
        val values = ContentValues()
        values.put(CrimeDbSchema.UUID, crime.mId.toString())
        values.put(CrimeDbSchema.TITLE, crime.mTitle)
        values.put(CrimeDbSchema.DATE, crime.mDate.time)
        values.put(CrimeDbSchema.SOLVED, when (crime.isSolved) {
            true -> 1
            false -> 0
        })
        return values
    }

    fun queryCrimes(whereClause: String?, whereArgs: Array<String>?) : CrimeCursorWrapper {
        val cursor = mDatabase.query(CrimeDbSchema.NAME, null, whereClause, whereArgs, null, null, null)
        return CrimeCursorWrapper(cursor)
    }
    companion object : SingletonHolder<CrimeLab, Context>
    (::CrimeLab)
}