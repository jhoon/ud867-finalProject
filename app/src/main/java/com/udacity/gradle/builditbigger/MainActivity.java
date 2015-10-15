package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import pe.jota.builditbigger.backend.myApi.MyApi;
import pe.jota.joketeller.JokeActivity;


public class MainActivity extends AppCompatActivity {
    View mJokeCallerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        // setting the view that is calling the tellJoke method
        // and disabling it to prevent calling the backend multiple times
        mJokeCallerView = view;
        mJokeCallerView.setEnabled(false);
        new JokeTask().execute();
    }

    private class JokeTask extends AsyncTask<Void, Void, String>{


        @Override
        protected String doInBackground(Void... params) {
            // Obtaining the ApiService, using the local ip address for the emulator
            MyApi myApiService;
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/");

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

            // enabling the view calling the joke, as it finished processing.
            mJokeCallerView.setEnabled(true);

            // Creating an intent to open the next Activity (from the Library)
            Intent intent = new Intent(MainActivity.this, JokeActivity.class);
            intent.putExtra(JokeActivity.PARAM_JOKE, jokeString);
            startActivity(intent);
        }
    }
}
