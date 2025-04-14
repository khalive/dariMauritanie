package mr.example.darimaritanie;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmailAddressLog, edtPassword;
    private Button btnLoginLog, btnRegisterLog;
    private TextView txtDisplayInfLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailAddressLog = findViewById(R.id.edtEmailAddressLog);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginLog = findViewById(R.id.btnLoginLog);
        btnRegisterLog = findViewById(R.id.btnRegisterLog);
        txtDisplayInfLog = findViewById(R.id.txtDisplayInfLog);

        btnLoginLog.setOnClickListener(v -> {
            String email = edtEmailAddressLog.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showError("Veuillez remplir tous les champs.");
            } else if (!isValidEmail(email)) {
                showError("Veuillez entrer une adresse email valide.");
            } else {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        btnRegisterLog.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showError(String message) {
        txtDisplayInfLog.setText(message);
        txtDisplayInfLog.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        txtDisplayInfLog.setVisibility(View.VISIBLE);
    }
}