package com.example.userlistapp.viewmodel;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.userlistapp.model.UserRepository;
import com.example.userlistapp.model.data.User;
import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository repository;
    private LiveData<List<User>> users;

    public UserViewModel(UserRepository repository) {
        this.repository = repository;
        users = repository.getUsers();
    }


    public LiveData<List<User>> getUsers() {
        return users;
    }


}