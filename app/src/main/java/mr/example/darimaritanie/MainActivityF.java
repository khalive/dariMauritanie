package mr.example.darimaritanie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivityF extends AppCompatActivity {
    private EditText edtEmailAddressLog, edtPassword;
    private Button btnLoginLog, btnRegisterLog;
    private TextView txtDisplayInfLog;
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "http://10.0.2.2:8081/api/auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainf);

        // Initialize views
        edtEmailAddressLog = findViewById(R.id.edtEmailAddressLog);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginLog = findViewById(R.id.btnLoginLog);
        btnRegisterLog = findViewById(R.id.btnRegisterLog);
        txtDisplayInfLog = findViewById(R.id.txtDisplayInfLog);

        // Set click listeners
        btnLoginLog.setOnClickListener(v -> loginUser());
        btnRegisterLog.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityF.this, RegisterF.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = edtEmailAddressLog.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty()) {
            showError("Please enter your email address");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError("Please enter a valid email address");
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter your password");
            return;
        }



        // Create JSON payload
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {
            showError("Error creating login request");
            return;
        }

        // Create request
        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + "/login")
                .post(body)
                .build();

        // Make network call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> showError("Network error: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        showSuccess("Login successful!");
                        // Redirect to main activity
                        Intent intent = new Intent(MainActivityF.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            JSONObject errorJson = new JSONObject(responseBody);
                            String errorMessage = errorJson.optString("message", "Login failed");
                            showError(errorMessage);
                        } catch (Exception e) {
                            showError("Login failed: " + responseBody);
                        }
                    }
                });
            }
        });
    }

    private void showError(String message) {
        txtDisplayInfLog.setText(message);
        txtDisplayInfLog.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        txtDisplayInfLog.setVisibility(View.VISIBLE);
    }

    private void showSuccess(String message) {
        txtDisplayInfLog.setText(message);
        txtDisplayInfLog.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        txtDisplayInfLog.setVisibility(View.VISIBLE);
    }
}