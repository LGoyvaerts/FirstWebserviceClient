package com.apprentice.ti8m.myfirstrestclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseText = (TextView) findViewById(R.id.responseText);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        //GET List Resources
        Call call = apiInterface.getPizzas();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("TAG", response.code() + " ");
                String displayResponse = "";
                List<Pizza> pizzas = (List<Pizza>) response.body();
                if (pizzas != null) {
                    for (Pizza p : pizzas) {
                        String name = p.getName();
                        Float price = p.getPrice();
                        Integer id = p.getId();
                        displayResponse += id + ": " + name + "=" + price + "\n";
                    }

                }
                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });

    }
}
