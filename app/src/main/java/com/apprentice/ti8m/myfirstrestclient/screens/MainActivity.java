package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.asynctask.PizzaLoadingTask;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.screens.login.LoginActivity;
import com.apprentice.ti8m.myfirstrestclient.screens.signup.SignUpActivity;

import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataAdapter adapter;
    ImageView emptyListImageView;
    TextView emptyListTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PizzaLoadingTask.TaskListener listener = new PizzaLoadingTask.TaskListener() {
            @Override
            public void onComplete(List<Pizza> pizzas) {
                if (pizzas.isEmpty()){
                    initViewsWithEmptyList();
                }
                initViews(pizzas);
            }
        };
        new PizzaLoadingTask(listener, APIClient.getInstance()).execute();
    }

    private void initViews(List<Pizza> pizzas) {
        recyclerView = findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new DataAdapter(pizzas);
        recyclerView.setAdapter(adapter);
    }

    private void initViewsWithEmptyList() {
        recyclerView = findViewById(R.id.card_recycler_view);
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
        if (preferences.contains("loggedIn")){
            Intent i = new Intent(this, LoginActivity.class); // Your list's Intent
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(i);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }


}
