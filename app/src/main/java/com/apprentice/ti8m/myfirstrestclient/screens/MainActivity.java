package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.asynctask.PizzaLoadingTask;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.screens.login.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PizzaLoadingTask.TaskListener listener = new PizzaLoadingTask.TaskListener() {
            @Override
            public void onComplete(List<Pizza> pizzas) {
                initViews(pizzas);
            }
        };
        new PizzaLoadingTask(listener).execute();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences preferences = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
        if (preferences.contains("loggedIn")){
            Intent i = new Intent(this, LoginActivity.class); // Your list's Intent
            i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY); // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(i);
        }else {
            SignUpActivity.start(MainActivity.this);
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }


}
