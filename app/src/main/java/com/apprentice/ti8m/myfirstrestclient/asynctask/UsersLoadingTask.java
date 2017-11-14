package com.apprentice.ti8m.myfirstrestclient.asynctask;

import android.os.AsyncTask;

import com.apprentice.ti8m.myfirstrestclient.api.APIInterface;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gol on 03.10.17.
 */

public class UsersLoadingTask extends AsyncTask<Void, Void, Boolean> {

    private String email;
    private String encryptedPassword;
    private TaskListener mListener;

    public UsersLoadingTask(TaskListener listener, String email, String encryptedPassword) {
        this.mListener = listener;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        final Boolean[] isValid = new Boolean[1];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIInterface request = retrofit.create(APIInterface.class);
        Call<Void> call = request.createUser(new User(email, encryptedPassword));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isValid[0] = true;
                } else {
                    isValid[0] = false;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isValid[0] = false;
            }
        });
        return isValid[0];
    }

    @Override
    protected void onPostExecute(Boolean isValid) {
        mListener.onComplete(isValid);

    }

    public interface TaskListener {
        void onComplete(Boolean isValid);
    }
}
