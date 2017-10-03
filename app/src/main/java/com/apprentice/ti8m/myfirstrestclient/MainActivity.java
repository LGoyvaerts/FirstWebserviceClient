package com.apprentice.ti8m.myfirstrestclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apprentice.ti8m.myfirstrestclient.model.JSONResponse;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Pizza> data;
    DataAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DataAdapter(data);
        recyclerView.setAdapter(adapter);
        Pizza pizza1 = new Pizza("Testpizza111", 18.50f, 1);
        Pizza pizza2 = new Pizza("Testpizza222", 19.50f, 2);
        Pizza pizza3 = new Pizza("Testpizza333", 20.50f, 3);
//        data = new ArrayList<>();
//        data.add(pizza1);
//        data.add(pizza2);
//        data.add(pizza3);
        loadJSON();
//        adapter = new DataAdapter(data);
//        recyclerView.setAdapter(adapter);
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.36.64:8080/pizzashop/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface request = retrofit.create(APIInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    JSONResponse jsonResponse = response.body();
                    data = new ArrayList<>(Arrays.asList(jsonResponse.getPizzas()));
                    //adapter = new DataAdapter(data);
                    //recyclerView.setAdapter(adapter);
                } else
                    response.errorBody();
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }


}
