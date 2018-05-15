package database

import android.content.Context
import android.database.Cursor
import android.database.CursorWrapper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bignerdranch.android.criminalintent.Crime
import java.util.*

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL("create table " + CrimeDbSchema.NAME + "(" +
       CrimeDbSchema.UUID + ", " +
       CrimeDbSchema.TITLE + ", " +
       CrimeDbSchema.DATE + ", " +
       CrimeDbSchema.SOLVED + ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val VERSION = 1
        private val DATABASE_NAME = "clientBAse.db"
    }
}

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getCrime() : Crime? {
        val uuidstring = getString(getColumnIndex(CrimeDbSchema.UUID))
        val title = getString(getColumnIndex(CrimeDbSchema.TITLE))
        val date = getLong(getColumnIndex(CrimeDbSchema.DATE))
        val isSolved = getInt(getColumnIndex(CrimeDbSchema.SOLVED))
        val crime = Crime(UUID.fromString(uuidstring))
        crime.mTitle = title
        crime.mDate = Date(date)
        crime.isSolved = isSolved != 0
        return crime
    }
}