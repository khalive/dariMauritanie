package mr.example.darimaritanie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mr.example.darimaritanie.MainActivityF;
import mr.example.darimaritanie.R;
import mr.example.darimaritanie.DetailsActivity;
import mr.example.darimaritanie.model.House;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    private Context context;
    private List<House> houseList;

    public HouseAdapter(Context context, List<House> houseList) {
        this.context = context;
        this.houseList = houseList;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_house, parent, false);
        return new HouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        House house = houseList.get(position);

        int imageRes = context.getResources().getIdentifier(house.imageName, "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageRes);

        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("location", house.location);
            intent.putExtra("owner", house.owner);
            intent.putExtra("price", house.price);
            intent.putExtra("forSale", house.forSale);
            intent.putExtra("phone", house.phone);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return houseList.size();
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button detailsButton;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.houseImage);
            detailsButton = itemView.findViewById(R.id.buttonDetails);
        }
    }
}