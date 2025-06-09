package mr.example.darimaritanie;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView detailsImage = findViewById(R.id.detailsImage);
        TextView infoText = findViewById(R.id.infoText);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Get chambre data from Intent
        String titre = getIntent().getStringExtra("titre");
        String description = getIntent().getStringExtra("description");
        double prix = getIntent().getDoubleExtra("prix", 0);
        String emplacement = getIntent().getStringExtra("emplacement");
        String wilaya = getIntent().getStringExtra("wilaya");
        String moughataa = getIntent().getStringExtra("moughataa");
        String imgUrl = getIntent().getStringExtra("imgUrl");

        // Build info text
        String message = "Titre: " + titre + "\n"
                + "Description: " + description + "\n"
                + "Prix: " + prix + "\n"
                + "Emplacement: " + emplacement + "\n"
                + "Wilaya: " + wilaya + "\n"
                + "Moughataa: " + moughataa;

        infoText.setText(message);

        // Load image from backend using Glide
        Glide.with(this)
                .load("http://10.0.2.2:8081" + imgUrl)
                .placeholder(R.drawable.placeholder)
                .into(detailsImage);

        buttonBack.setOnClickListener(v -> finish());
    }
}