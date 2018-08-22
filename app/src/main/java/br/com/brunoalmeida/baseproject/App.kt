package br.com.brunoalmeida.baseproject

import android.app.Application
import android.arch.persistence.room.Room
import br.com.brunoalmeida.baseproject.common.model.database.AppDataBase
import br.com.brunoalmeida.baseproject.common.model.api.RetrofitClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        retorfitClient = RetrofitClient()
        database = Room.databaseBuilder(
                this,
                AppDataBase::class.java,
                "app-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_2)
                .build()

    }

    companion object {
        lateinit var database: AppDataBase
        lateinit var mInstance: App
        lateinit var retorfitClient: RetrofitClient

        fun <S> getService(service: Class<S>): S {
            return retorfitClient.createService(service)
        }
    }
}