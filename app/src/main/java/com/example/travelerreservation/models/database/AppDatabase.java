package com.example.travelerreservation.models.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.travelerreservation.models.UserDao;
import com.example.travelerreservation.models.UserEntity;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
