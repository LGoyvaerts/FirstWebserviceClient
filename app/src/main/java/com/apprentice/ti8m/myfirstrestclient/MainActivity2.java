package com.apprentice.ti8m.myfirstrestclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        responseText = (TextView) findViewById(R.id.responseText);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        //GET List Resources
        Call call = apiInterface.getPizzas();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("TAG", response.code() + " ");
                List<Pizza> pizzas = (List<Pizza>) response.body();

               // ArrayAdapter<Pizza> adapter = new ArrayAdapter<Pizza>(this, android.R.layout.simple_list_item_1, pizzas);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }

}
