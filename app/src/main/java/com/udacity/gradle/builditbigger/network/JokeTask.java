package com.udacity.gradle.builditbigger.network;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import pe.jota.builditbigger.backend.myApi.MyApi;

/**
 * Created by jhoon on 10/15/15.
 * Task in charge of pulling the jokes from the backend.
 */
public class JokeTask extends AsyncTask<Void, Void, String> {

    /**
     * Callback interface, has to be implemented by users of this task
     */
    public interface Callback {
        void onPostExecute(String jokeString);
    }

    /**
     * Callback class where to send the resulting data
     */
    private Callback mCallback;

    /**
     * sets the class that will be called for the callbacks
     * @param callback class that implements the JokeTask.Callback interface
     */
    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        // Obtaining the ApiService, using the local ip address for the emulator
        MyApi myApiService;
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),null)
                .setRootUrl("https://sandbox-project-1022.appspot.com/_ah/api/"); // Setting the local ip of appengine

        myApiService = builder.build();

        try {
            // Obtains the string with the joke, returned form the AppEngine backend
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String jokeString) {
        super.onPostExecute(jokeString);
        mCallback.onPostExecute(jokeString);
    }
}
