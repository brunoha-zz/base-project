package br.com.brunoalmeida.baseproject.common.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [],version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase()