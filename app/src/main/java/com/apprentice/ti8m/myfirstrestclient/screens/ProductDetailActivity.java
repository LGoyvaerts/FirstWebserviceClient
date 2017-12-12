package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

/**
 * Created by gol on 12.12.17.
 * Don't copy my Stuff!
 */

public class ProductDetailActivity extends AppCompatActivity {

    Pizza pizza;
    TextView pizza_name;
    TextView pizza_price;
    ListView pizza_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        initViews();

        pizza_name.setText(bundle.getString("Name"));
        Float price = bundle.getFloat("Price");
        pizza_price.setText(price.toString());
        String[] ingredients = bundle.getString("Ingredients").split(", ");
        for (String s : ingredients){
            TextView ingredient = new TextView(this);
            ingredient.setText(s);
            pizza_ingredients.addHeaderView(ingredient);
        }

    }

    private void initViews(){
        pizza_name = findViewById(R.id.product_description_textview);
        pizza_price = findViewById(R.id.product_price_description2);
        pizza_ingredients = findViewById(R.id.product_ingredients_listview);
    }
}
