package com.apprentice.ti8m.myfirstrestclient.screens;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gol on 02.10.17.
 * Don't copy my Stuff!
 */

public class PizzaDataAdapter extends RecyclerView.Adapter<PizzaDataAdapter.ViewHolder> {
    private List<Pizza> pizzas;

    PizzaDataAdapter(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    private static BigDecimal round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    @Override
    public PizzaDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pizza_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PizzaDataAdapter.ViewHolder viewHolder, int i) {

        //viewHolder.pizza_image.setText(pizzas.get(i).getId().toString());
        viewHolder.pizza_name.setText(pizzas.get(i).getName());
        BigDecimal result;
        result = round(pizzas.get(i).getPrice());
        result = round(pizzas.get(i).getPrice());
        String price = "Price: " + result.toString();
        viewHolder.pizza_price.setText(price);

        GlideApp.with(viewHolder.pizza_image.getContext())
                .load(pizzas.get(i).getImagepath())
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.pizza_image);

    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView pizza_image;
        private TextView pizza_name, pizza_price;

        ViewHolder(View view) {
            super(view);

            pizza_image = view.findViewById(R.id.pizza_image);

            pizza_name = view.findViewById(R.id.pizza_name);
            pizza_price = view.findViewById(R.id.pizza_price);
        }
    }
}
