package com.apprentice.ti8m.myfirstrestclient.screens;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.model.Drink;
import com.apprentice.ti8m.myfirstrestclient.utils.GlideApp;
import com.bumptech.glide.request.RequestOptions;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gol on 02.10.17.
 * Don't copy my Stuff!
 */

public class DrinkDataAdapter extends RecyclerView.Adapter<DrinkDataAdapter.ViewHolder> {
    private List<Drink> drinks;

    public DrinkDataAdapter(List<Drink> drinks) {
        this.drinks = drinks;
    }

    private static BigDecimal round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    @Override
    public DrinkDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drink_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrinkDataAdapter.ViewHolder viewHolder, int i) {

        //viewHolder.drink_image.setText(drinks.get(i).getId().toString());
        viewHolder.drink_name.setText(drinks.get(i).getName());
        BigDecimal result;
        result = round(drinks.get(i).getPrice());
        result = round(drinks.get(i).getPrice());
        String price = "Price: " + result.toString();
        viewHolder.drink_price.setText(price);

        GlideApp.with(viewHolder.drink_image.getContext())
                .load(drinks.get(i).getImagepath())
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.drink_image);

    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView drink_image;
        private TextView drink_name, drink_price;

        ViewHolder(View view) {
            super(view);

            drink_image = view.findViewById(R.id.drink_image);

            drink_name = view.findViewById(R.id.drink_name);
            drink_price = view.findViewById(R.id.drink_price);
        }
    }
}
