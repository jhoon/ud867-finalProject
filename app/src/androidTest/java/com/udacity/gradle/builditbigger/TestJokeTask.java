package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;

import com.udacity.gradle.builditbigger.network.JokeTask;

import java.util.concurrent.CountDownLatch;

/**
 * Created by jhoon on 10/15/15.
 * Tests the AsyncTask that pulls jokes from the backend.
 */
public class TestJokeTask extends AndroidTestCase implements JokeTask.Callback {

    CountDownLatch mCountdown;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Initiating the countDownLatch to expect only one execution
        mCountdown = new CountDownLatch(1);

    }

    public void testJokeTask() throws InterruptedException {
        // Setting up the JokeTask to retrieve a joke
        JokeTask jokeTask = new JokeTask();
        jokeTask.setCallback(this);
        jokeTask.execute();

        mCountdown.await();
    }

    @Override
    public void onPostExecute(String jokeString) {
        assertNotNull(jokeString);
        mCountdown.countDown();
    }
}
