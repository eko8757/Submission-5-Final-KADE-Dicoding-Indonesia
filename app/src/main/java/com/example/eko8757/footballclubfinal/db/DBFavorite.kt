package com.example.eko8757.footballclubfinal.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DBFavorite(context: Context): ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1) {

    companion object {
        private var instance: DBFavorite? = null

        @Synchronized
    fun getInstance(context: Context): DBFavorite{
            if (instance == null) {
                instance = DBFavorite(context.applicationContext)
            }
            return instance as DBFavorite
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(DBEvent.TABLE_EVENT, true,
                DBEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DBEvent.EVENT_ID to TEXT + UNIQUE,
                DBEvent.HOMETEAM_ID to TEXT,
                DBEvent.HOMETEAM to TEXT,
                DBEvent.AWAYTEAM_ID to TEXT,
                DBEvent.AWAYTEAM to TEXT,
                DBEvent.HOMESCORE to TEXT,
                DBEvent.AWAYSCORE to TEXT,
                DBEvent.DATEEVENT to TEXT)

        db?.createTable(DBTeam.TABLE_TEAMS, true,
                DBTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                DBTeam.TEAMS_ID to TEXT + UNIQUE,
                DBTeam.TEAMS_NAME to TEXT,
                DBTeam.BADGE_TEAM to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(DBEvent.TABLE_EVENT, true)
        db?.dropTable(DBTeam.TABLE_TEAMS, true)
    }
}

val Context.database: DBFavorite
get() = DBFavorite.getInstance(applicationContext)