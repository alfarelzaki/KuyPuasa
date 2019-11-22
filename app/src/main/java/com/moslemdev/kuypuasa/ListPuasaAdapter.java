package com.moslemdev.kuypuasa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListPuasaAdapter extends RecyclerView.Adapter<ListPuasaAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Puasa> values;

    public ListPuasaAdapter(Context context, ArrayList<Puasa> values) {
        this.context = context;
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ListPuasaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_puasa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPuasaAdapter.ViewHolder holder, int position) {
        final Puasa data = values.get(position);
        holder.viewPuasa.setText(data.puasa);
        holder.viewDeskripsi.setText(data.deskripsi);
        holder.viewExperience.setText(data.experience);
        Glide.with(holder.itemView.getContext())
                .load(data.getTanda())
                .apply(new RequestOptions().override(35, 35))
                .into(holder.viewTanda);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView viewPuasa;
        TextView viewDeskripsi;
        TextView viewExperience;
        ImageView viewTanda;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPuasa = itemView.findViewById(R.id.nama_puasa);
            viewDeskripsi = itemView.findViewById(R.id.deskripsi_puasa);
            viewExperience = itemView.findViewById(R.id.text_experience);
            viewTanda = itemView.findViewById(R.id.tanda_puasa);
        }
    }
}
