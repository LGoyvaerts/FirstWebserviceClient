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
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>{
    private List<Pizza> pizzas;

    public DataAdapter(List<Pizza> pizzas) {
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
        BigDecimal result;
        result = round(pizzas.get(i).getPrice(), 2);
        String price = "Price: "+result.toString();
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

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}
