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

public class RegisterF extends AppCompatActivity {
    private EditText edtFullnameReg, edtEmailAddressReg, edtPasswordReg, edtPhonNumberReg,edtBioReg;
    private Button btnRegisterReg, btnLoginReg;


    private TextView txtDisplayInfReg;
    private final OkHttpClient client = new OkHttpClient();
    private static final String BASE_URL = "http://10.0.2.2:8081/api/auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerf);

        edtFullnameReg = findViewById(R.id.edtFullnameReg);
        edtEmailAddressReg = findViewById(R.id.edtEmailAddressReg);
        edtPasswordReg = findViewById(R.id.edtPasswordReg);
        edtPhonNumberReg = findViewById(R.id.edtPhonNumberReg);
        edtBioReg = findViewById(R.id.edtBioReg);
        btnRegisterReg = findViewById(R.id.btnRegisterReg);
        btnLoginReg = findViewById(R.id.btnLoginReg);
        txtDisplayInfReg = findViewById(R.id.txtDisplayInfLog); // Add this TextView to your XML if not present

        btnRegisterReg.setOnClickListener(v -> registerUser());
        btnLoginReg.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterF.this, MainActivityF.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String name = edtFullnameReg.getText().toString().trim();
        String email = edtEmailAddressReg.getText().toString().trim();
        String password = edtPasswordReg.getText().toString().trim();
        String phonenumber = edtPhonNumberReg.getText().toString().trim();
        String biography = edtBioReg.getText().toString().trim();

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("email", email);
            json.put("password", password);
            json.put("phonenumber", phonenumber);
            json.put("biography", biography);

        } catch (Exception e) {
            showError("Erreur de saisie.");
            return;
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(BASE_URL + "/register")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> showError("Erreur réseau : " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        showSuccess("Inscription réussie !");
                        Intent intent = new Intent(RegisterF.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showError("Erreur : " + responseBody);
                    }
                });
            }
        });
    }

    private void showError(String message) {
        txtDisplayInfReg.setText(message);
        txtDisplayInfReg.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        txtDisplayInfReg.setVisibility(View.VISIBLE);
    }

    private void showSuccess(String message) {
        txtDisplayInfReg.setText(message);
        txtDisplayInfReg.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        txtDisplayInfReg.setVisibility(View.VISIBLE);
    }
}