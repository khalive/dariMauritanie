package mr.example.darimaritanie;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView welcomeText = findViewById(R.id.welcomeText);
        Button btnBackToLogin = findViewById(R.id.btnBackToLogin);

        String email = getIntent().getStringExtra("email");

        // Extract name before @
        String username = email.split("@")[0];
        welcomeText.setText("Tfou 3Likm , " + username + "!");

        // Handle back to login button click
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                finish(); // Close current activity and return to previous one (Login)
            }
        });
    }
}