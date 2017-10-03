package com.apprentice.ti8m.myfirstrestclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by gol on 02.10.17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private ArrayList<Pizza> pizzas;

    public DataAdapter(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        //viewHolder.pizza_image.setText(pizzas.get(i).getId().toString());
        viewHolder.pizza_name.setText(pizzas.get(i).getName());
        String price = pizzas.get(i).getPrice().toString();
        viewHolder.pizza_price.setText(price);
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pizza_image;
        private TextView pizza_name, pizza_price;

        public ViewHolder(View view) {
            super(view);

            pizza_image = view.findViewById(R.id.imageView);
            GlideApp.with(view.getContext())
                    .load(R.drawable.samplepic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(pizza_image);
            pizza_name = view.findViewById(R.id.pizza_name);
            pizza_price = view.findViewById(R.id.pizza_price);

        }
    }
}
