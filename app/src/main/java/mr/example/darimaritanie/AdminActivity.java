package mr.example.darimaritanie;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_admin_house); // matches the layout file

        Button approveButton = findViewById(R.id.buttonApprove);
        Button rejectButton = findViewById(R.id.buttonReject);
        Button detailsButton = findViewById(R.id.buttonDetails);

        approveButton.setOnClickListener(v -> {
            //TODO: handle approve
        });

        rejectButton.setOnClickListener(v -> {
            // TODO: handle approve
        });

        detailsButton.setOnClickListener(v -> {
            // TODO: show details
        });
    }
}
