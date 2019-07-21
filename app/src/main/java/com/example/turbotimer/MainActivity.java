package com.example.turbotimer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar countdownProgressBar;
    TextView remainingTimeLabel;
    Context context;
    CountDownTimer timer;
    Button startButton;
    Button cancelButton;
    Integer spoolDownTimeInSeconds = 2 * 60;
    Integer spoolDownTimeInMilliseconds = spoolDownTimeInSeconds * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        countdownProgressBar = findViewById(R.id.countdownProgress);
        remainingTimeLabel = findViewById(R.id.remainingTime);
        startButton = findViewById(R.id.startButton);
        cancelButton = findViewById(R.id.cancelButton);
        context = getApplicationContext();

        setDefaultTimeLabel();
        cancelButton.setVisibility(View.INVISIBLE);
        countdownProgressBar.setProgress(100, true);
    }

    public void startTimer(View v) {

        cancelButton.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);

        timer = new CountDownTimer(spoolDownTimeInMilliseconds, 333) {

            public void onTick(long millisUntilFinished) {
                Float progressValue = ((float)millisUntilFinished / spoolDownTimeInMilliseconds);
                countdownProgressBar.setProgress(Math.round(progressValue*100), true);
                String remainingTimeString = constructRemainingTimeLabel(millisUntilFinished);
                setRemainingTimeLabel(remainingTimeString);
            }

            public void onFinish() {
                countdownProgressBar.setProgress(100, true);
                remainingTimeLabel.setText(R.string.timer_complete);
                Toast.makeText(context, "Turbos have spooled down!", Toast.LENGTH_LONG).show();

            }
        };

        timer.start();

    }

    public void cancelTimer(View v) {
        timer.cancel();
        setDefaultTimeLabel();
    }

    private void setDefaultTimeLabel() {
        String defaultTimerLabel = constructRemainingTimeLabel(spoolDownTimeInMilliseconds);
        setRemainingTimeLabel(defaultTimerLabel);
    }

    private void setRemainingTimeLabel(String remainingTime) {
        remainingTimeLabel.setText(remainingTime);
    }

    private String constructRemainingTimeLabel (long msRemaining) {
        Integer minutes = getNumberOfMinutesRemaining(msRemaining);
        Integer seconds = getNumberOfSecondsRemaining(msRemaining);
        String secondsString;

        if (seconds < 10) {
            secondsString = "0" + seconds.toString();
        } else {
            secondsString = seconds.toString();
        }

        return minutes.toString() + ":" + secondsString;
    }

    private Integer getNumberOfMinutesRemaining(long msRemaining) {
        Integer secondsRemaining =  (int) msRemaining / 1000;
        return (int) Math.floor(secondsRemaining / 60);
    }

    private Integer getNumberOfSecondsRemaining(long msRemaining) {
        Integer secondsRemaining = (int) msRemaining / 1000;
        return secondsRemaining % 60;
    }
}
