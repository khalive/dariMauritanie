package mr.example.darimaritanie;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView infoText = findViewById(R.id.infoText);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Récupérer les données envoyées par Intent
        String location = getIntent().getStringExtra("location");
        String owner = getIntent().getStringExtra("owner");
        String price = getIntent().getStringExtra("price");
        boolean forSale = getIntent().getBooleanExtra("forSale", true);
        String phone = getIntent().getStringExtra("phone");

        // Construire le texte à afficher
        String status = forSale ? "For Sale" : "For Rent";
        String message = "Location: " + location + "\n"
                + "Owner: " + owner + "\n"
                + "Price: " + price + "\n"
                + "Status: " + status + "\n"
                + "Phone: " + phone;

        infoText.setText(message);

        // Bouton retour
        buttonBack.setOnClickListener(v -> {
            finish(); // Fermer l'activité actuelle pour revenir
        });
    }
}
