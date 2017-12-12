package com.apprentice.ti8m.myfirstrestclient.asynctask;

import android.os.AsyncTask;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.api.APIInterface;
import com.apprentice.ti8m.myfirstrestclient.model.Drink;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

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

public class DrinkLoadingTask extends AsyncTask<Void, Void, List<Drink>> {

    private APIClient apiClient;
    private TaskListener mListener;

    public DrinkLoadingTask(TaskListener listener, APIClient apiClient){
        this.mListener=listener;
        this.apiClient = apiClient;
    }

    @Override
    protected List<Drink> doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface request = retrofit.create(APIInterface.class);
        Call<List<Drink>> call = request.getDrinks();

        try {
            Response<List<Drink>> jsonResponse = call.execute();
            return jsonResponse.body();
            // return Collections.emptyList();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Drink> drinks) {
        mListener.onComplete(drinks);

    }

    public interface TaskListener {
        void onComplete(List<Drink> drinks);
    }
}
