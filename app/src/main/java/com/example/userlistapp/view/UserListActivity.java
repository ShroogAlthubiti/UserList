package com.example.userlistapp.view;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.userlistapp.R;
import com.example.userlistapp.model.UserAdapter;
import com.example.userlistapp.model.UserRepository;
import com.example.userlistapp.model.data.AppDatabase;
import com.example.userlistapp.model.data.AppDatabaseSingleton;
import com.example.userlistapp.model.data.User;
import com.example.userlistapp.viewmodel.UserViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserListActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    //private UserRepository repository;
    private UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        try {
            Log.d("UserListActivityCreate", "onCreate: started");
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new UserAdapter();
            recyclerView.setAdapter(adapter);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                AppDatabase db = AppDatabaseSingleton.getInstance(getApplicationContext());
               UserRepository repository = new UserRepository(db.userDao());
                runOnUiThread(() -> {
                    userViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory() {
                        @NonNull
                        @Override
                        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                            Log.e("UserListActivityView", "Database and repository setup complete");
                            return (T) new UserViewModel(repository);
                        }
                    }).get(UserViewModel.class);

                    userViewModel.getUsers().observe(this, users -> {
                        Log.e("UserListActivity", "Users received: " + (users != null ? users.size() : "null"));
                        if (users != null && !users.isEmpty()) {
                            adapter.setUsers(users);
                            Log.e("UserListActivity", "Adapter setUsers called with data");
                        } else {
                            Log.e("UserListActivity", "No users received");
                        }
                    });

                    Log.d("UserListActivity", "Data fetch initiated");
                });
            });
       } catch (Exception e){
            Log.e("UserListActivity", "Error in onCreate: " + e.getMessage(), e);
        }
    }
}
