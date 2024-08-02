package com.example.userlistapp.model.api;

import com.example.userlistapp.model.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("users")
    Call<ApiResponse<List<User>>> getUsers();
}
