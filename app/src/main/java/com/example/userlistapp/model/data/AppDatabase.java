package com.example.userlistapp.model.data;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version =2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}