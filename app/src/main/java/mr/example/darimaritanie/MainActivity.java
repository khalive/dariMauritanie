package mr.example.darimaritanie;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mr.example.darimaritanie.adapter.HouseAdapter;
import mr.example.darimaritanie.model.House;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<House> houseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.item_house);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        houseList = new ArrayList<>();
        houseList.add(new House("house1", "Paris", "Ali", "300000€", true, "0606060606"));
        houseList.add(new House("house2", "Lyon", "Fatima", "2000€/mois", false, "0707070707"));
        houseList.add(new House("house3", "Marseille", "Omar", "250000€", true, "0605050505"));
        houseList.add(new House("house5", "Toulouse", "Said", "1800€/mois", false, "0656565656"));
// etc.


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HouseAdapter(this, houseList));
    }
}