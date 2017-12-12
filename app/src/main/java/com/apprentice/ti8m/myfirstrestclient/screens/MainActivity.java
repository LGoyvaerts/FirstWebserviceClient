package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.asynctask.DrinkLoadingTask;
import com.apprentice.ti8m.myfirstrestclient.asynctask.PizzaLoadingTask;
import com.apprentice.ti8m.myfirstrestclient.model.Drink;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.screens.login.LoginActivity;

import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PizzaDataAdapter pizzaDataAdapter;
    DrinkDataAdapter drinkDataAdapter;
    ImageView emptyListImageView;
    TextView emptyListTextView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_food:
                    FrameLayout pizzas_layout = findViewById(R.id.pizzas_layout);
                    FrameLayout drinks_layout = findViewById(R.id.drinks_layout);
                    drinks_layout.setVisibility(GONE);
                    pizzas_layout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_drinks:
                    //TODO create DrinkActivitiy
                    drinks_layout = findViewById(R.id.drinks_layout);
                    pizzas_layout = findViewById(R.id.pizzas_layout);
                    pizzas_layout.setVisibility(GONE);
                    drinks_layout.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_cart:
                    //TODO create CartActivity
                    return true;
            }
            return false;
        }
    };

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        PizzaLoadingTask.TaskListener pizzaListener = new PizzaLoadingTask.TaskListener() {
            @Override
            public void onComplete(List<Pizza> pizzas) {
                if (pizzas.isEmpty()) {
                    initViewsWithEmptyList();
                }
                initPizzaViews(pizzas);
            }
        };
        new PizzaLoadingTask(pizzaListener, APIClient.getInstance()).execute();

        DrinkLoadingTask.TaskListener drinkListener = new DrinkLoadingTask.TaskListener() {
            @Override
            public void onComplete(List<Drink> drinks) {
                if (drinks.isEmpty()) {
                    initViewsWithEmptyList();
                }
                initDrinkViews(drinks);
            }
        };
        new DrinkLoadingTask(drinkListener, APIClient.getInstance()).execute();
    }

    private void initPizzaViews(List<Pizza> pizzas) {
        recyclerView = findViewById(R.id.card_recycler_view_pizzas);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.VERTICAL));
        pizzaDataAdapter = new PizzaDataAdapter(pizzas);
        recyclerView.setAdapter(pizzaDataAdapter);
    }

    private void initDrinkViews(List<Drink> drinks) {
        recyclerView = findViewById(R.id.card_recycler_view_drinks);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.VERTICAL));
        drinkDataAdapter = new DrinkDataAdapter(drinks);
        recyclerView.setAdapter(drinkDataAdapter);
    }

    private void initViewsWithEmptyList() {
        recyclerView = findViewById(R.id.card_recycler_view_drinks);
        recyclerView.setVisibility(GONE);
        emptyListImageView = findViewById(R.id.pizza_image_empty_list);
        emptyListTextView = findViewById(R.id.pizza_empty_textview);
        emptyListImageView.setVisibility(View.VISIBLE);
        emptyListTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences preferences = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
        if (preferences.contains("loggedIn")) {
            Intent i = new Intent(this, LoginActivity.class); // Your list's Intent
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(i);
        }
    }


}
