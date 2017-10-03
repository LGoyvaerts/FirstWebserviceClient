package com.apprentice.ti8m.myfirstrestclient;

import android.os.AsyncTask;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

/**
 * Created by gol on 03.10.17.
 */

public class PizzaLoadingTask extends AsyncTask<Pizza, TextView, Long> {
    private OnTaskCompleted listener;
    @Override
    protected Long doInBackground(Pizza... pizzas) {
        publishProgress();
        long size = 0;
        for (Pizza p : pizzas) {
            size++;
        }
        return size;
    }

    @Override
    protected void onProgressUpdate(TextView... values) {
        values[0].setText("Loading");
    }

    @Override
    protected void onPostExecute(Long aLong) {

        listener.onTaskCompleted();
    }
}
