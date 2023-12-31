package com.example.travelerreservation.managers;

import androidx.room.Room;

import com.example.travelerreservation.models.database.AppDatabase;

public class DatabaseManager {
    private static DatabaseManager singleton;
    private final String databaseName = "appdb";
    private ContextManager contextManager;
    private AppDatabase database;

    //Returns DatabaseManager singleton object
    public static DatabaseManager getInstance(){
        if (singleton == null)
            singleton = new DatabaseManager();
        return singleton;
    }

    private DatabaseManager(){
        contextManager = ContextManager.getInstance();
        database = Room.databaseBuilder(
                contextManager.getApplicationContext(),
                AppDatabase.class,
                databaseName).build();
    }

    public AppDatabase db(){
        return database;
    }

}
