package mr.example.darimaritanie.adapter;

//package mr.example.darimaritanie.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import mr.example.darimaritanie.DetailsActivity;
import mr.example.darimaritanie.R;
import mr.example.darimaritanie.model.Chambre;

import java.util.List;
public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    private Context context;
    private List<Chambre> chambreList;

    public HouseAdapter(Context context, List<Chambre> chambreList) {
        this.context = context;
        this.chambreList = chambreList;
    }

    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_house, parent, false);
        return new HouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        Chambre chambre = chambreList.get(position);
        String imageUrl = "http://10.0.2.2:8081" + chambre.getImgUrl();
        Log.d("IMAGE_DEBUG", "Loading image from: " + imageUrl);
        // Load image from backend using Glide
        Glide.with(context)
                .load("http://10.0.2.2:8081" + chambre.getImgUrl())
                .placeholder(R.drawable.placeholder) // Add a placeholder image to drawable
                .into(holder.imageView);
//        holder.statusText.setText(chambre.getStatut().toString());

//        if ("DISPONIBLE".equals(chambre.getStatut().toString())) {
//            holder.statusText.setBackgroundResource(R.drawable.status_available_bg);
//        } else {
//            holder.statusText.setBackgroundResource(R.drawable.status_unavailable_bg);
//        }

        // Set the status text dynamically
        holder.statusText.setText(chambre.getStatut());

        // Optionally, change background color based on status
        String statut = chambre.getStatut();
        if ("DISPONIBLE".equals(statut)) {
            holder.statusText.setBackgroundResource(R.drawable.status_available_bg);
        } else if ("ALLOUEE".equals(statut)) {
            holder.statusText.setBackgroundResource(R.drawable.status_unavailable_bg);
        } else if ("EN_ATTENTE".equals(statut)) {
            holder.statusText.setBackgroundResource(R.drawable.status_background);
        } else {
            holder.statusText.setBackgroundResource(R.drawable.status_background);
        }

        holder.detailsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("titre", chambre.getTitre());
            intent.putExtra("description", chambre.getDescription());
            intent.putExtra("prix", chambre.getPrix());
            intent.putExtra("emplacement", chambre.getEmplacement());
            intent.putExtra("wilaya", chambre.getWilaya());
            intent.putExtra("moughataa", chambre.getMoughataa());
            intent.putExtra("imgUrl", chambre.getImgUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chambreList.size();
    }

    public static class HouseViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button detailsButton;

        TextView statusText;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.houseImage);
            detailsButton = itemView.findViewById(R.id.buttonDetails);
            statusText = itemView.findViewById(R.id.statusText);
        }
    }
}