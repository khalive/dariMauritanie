package mr.example.darimaritanie;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mr.example.darimaritanie.adapter.HouseAdapter;
import mr.example.darimaritanie.api.ChambreApi;
import mr.example.darimaritanie.model.Chambre;
import mr.example.darimaritanie.model.ChambreResponse;
//import mr.example.darimaritanie.adapter.ChambreAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChambreApi api = retrofit.create(ChambreApi.class);
        api.getChambres().enqueue(new Callback<ChambreResponse>() {
            @Override
            public void onResponse(Call<ChambreResponse> call, Response<ChambreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Chambre> chambreList = response.body().getData();
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                    recyclerView.setAdapter(new ChambreAdapter(MainActivity.this, chambreList));
                    recyclerView.setAdapter(new HouseAdapter(MainActivity.this, chambreList));
                }
            }
            @Override
            public void onFailure(Call<ChambreResponse> call, Throwable t) {
                // Handle error (e.g., show a Toast)
            }
        });
    }
}