package com.example.userlistapp.model;

import static com.example.userlistapp.model.CryptoUtils.encrypt;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.userlistapp.model.api.ApiResponse;
import com.example.userlistapp.model.api.ApiService;
import com.example.userlistapp.model.api.RetrofitClient;
import com.example.userlistapp.model.data.User;
import com.example.userlistapp.model.data.UserDao;
import com.example.userlistapp.model.data.UserEntity;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepository {
    private ApiService apiService;
    private UserDao userDao;
    private ExecutorService executorService;

    public UserRepository(UserDao userDao) {
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        this.userDao = userDao;
        this.executorService = Executors.newSingleThreadExecutor();

    }


    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        fetchUsersFromApi(data);
        return data;
    }

    private void fetchUsersFromApi(MutableLiveData<List<User>> data) {
        apiService.getUsers().enqueue(new Callback<ApiResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<User>>> call, Response<ApiResponse<List<User>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getData();
                    data.postValue(users);
                    saveUsersToDatabase(users);
                    Log.d("UserRepository", "Fetched " + users.size() + " users from API");
                } else {
                    Log.e("UserRepository", "Response not successful: " + response.code() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<User>>> call, Throwable t) {
                Log.e("UserRepository", "API call failed: " + t.getMessage(), t);
            }
        });
    }


    private void saveUsersToDatabase(List<User> users) {
    executorService.execute(() -> {
        for (User user : users) {
            try {
                String encryptedName = encrypt(user.getName());
                String encryptedEmail = encrypt(user.getEmail());
                String encryptedGender = encrypt(user.getGender());
                String encryptedStatus = encrypt(user.getStatus());
                UserEntity encryptedUser = new UserEntity();
                encryptedUser.setId(user.getId());
                encryptedUser.setName(encryptedName);
                encryptedUser.setEmail(encryptedEmail);
                encryptedUser.setGender(encryptedGender);
                encryptedUser.setStatus(encryptedStatus);
                userDao.insertUser(encryptedUser);
                Log.d("UserRepository", "User inserted into database: " + user.getName());
            } catch (Exception e) {
                Log.e("UserRepository", "Error inserting user into database: ", e);
            }
        }
    });
}


    }



