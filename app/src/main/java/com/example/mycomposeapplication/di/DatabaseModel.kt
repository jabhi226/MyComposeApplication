//package com.example.mycomposeapplication.di
//
//import android.app.Application
//import androidx.room.Room
//import com.example.mycomposeapplication.data.LocalDatabase
//import com.example.mycomposeapplication.data.repo.Repository
//import com.example.mycomposeapplication.data.repo.RepositoryImp
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModel {
//
////    @Provides
////    @Singleton
////    fun provideDb(app: Application): LocalDatabase {
////        return Room.databaseBuilder(app, LocalDatabase::class.java, "item_db").build()
////    }
////
////    @Provides
////    @Singleton
////    fun provideRepo(db: LocalDatabase): Repository {
////        return RepositoryImp(db.itemDao)
////    }
//}