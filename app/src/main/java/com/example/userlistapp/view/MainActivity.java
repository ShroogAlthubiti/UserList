package com.example.userlistapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import com.example.userlistapp.R;

public class MainActivity extends AppCompatActivity {
    private WebSocket webSocket;
    private Button connectButton;
    private EditText messageInput;
    private boolean isWebSocketConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        connectButton = findViewById(R.id.btnConnect);
        messageInput = findViewById(R.id.edit_text_message);
        connectButton.setOnClickListener(v -> {
            if (!isWebSocketConnected) {
                connectWebSocket();
            } else {
                disconnectWebSocket();
            }
        });
        findViewById(R.id.btnSend).setOnClickListener(v -> {
            if (isWebSocketConnected) {
                String message = messageInput.getText().toString();
                webSocket.send(message);
            }
        });
        Button button = findViewById(R.id.btnUsersList);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(intent);
        });
    }


    private void connectWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("wss://echo.websocket.org").build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                runOnUiThread(() -> {
                    isWebSocketConnected = true;
                    connectButton.setText("Disconnect");
                    Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                runOnUiThread(() -> new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Received: " + text)
                        .setPositiveButton("OK", null)
                        .show());
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                runOnUiThread(() -> {
                    isWebSocketConnected = false;
                    connectButton.setText("Connect");
                    Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                runOnUiThread(() -> {
                    isWebSocketConnected = false;
                    connectButton.setText("Connect");
                });
            }
        });
    }

    private void disconnectWebSocket() {
        if (webSocket != null) {
            webSocket.close(1000, "Disconnected");
        }
        isWebSocketConnected = false;
        connectButton.setText("Connect");
    }


}