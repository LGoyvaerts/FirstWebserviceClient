package com.apprentice.ti8m.myfirstrestclient.asynctask;

import android.os.AsyncTask;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.api.APIInterface;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gol on 03.10.17.
 */

public class PizzaLoadingTask extends AsyncTask<Void, Void, List<Pizza>> {

    private APIClient apiClient;
    private TaskListener mListener;

    public PizzaLoadingTask(TaskListener listener, APIClient apiClient){
        this.mListener=listener;
        this.apiClient = apiClient;
    }

    @Override
    protected List<Pizza> doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface request = retrofit.create(APIInterface.class);
        Call<List<Pizza>> call = request.getPizzas();

        try {
            Response<List<Pizza>> jsonResponse = call.execute();
            return jsonResponse.body();
            // return Collections.emptyList();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Pizza> pizzas) {
        mListener.onComplete(pizzas);

    }

    public interface TaskListener {
        void onComplete(List<Pizza> pizzas);
    }
}
