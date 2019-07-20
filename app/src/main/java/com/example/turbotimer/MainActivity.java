package com.example.turbotimer;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar countdownProgressBar;
    TextView remainingTimeLabel;
    Context context;
    CountDownTimer timer;
    Integer spoolDownTimeInSeconds = 70;
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
        context = getApplicationContext();

    }

    public void startTimer(View v) {

        timer = new CountDownTimer(spoolDownTimeInMilliseconds, 333) {

            public void onTick(long millisUntilFinished) {
                Float progressValue = ((float)millisUntilFinished / spoolDownTimeInMilliseconds);
                Log.d("Progress", progressValue.toString());
                countdownProgressBar.setProgress(Math.round(progressValue*100), true);
                String remainingTimeString = constructRemainingTimeLabel(millisUntilFinished);
                setRemainingTimeLabel(remainingTimeString);
            }

            public void onFinish() {
                countdownProgressBar.setProgress(100, true);
                remainingTimeLabel.setText(R.string.timer_complete);
                Toast.makeText(context, "Turbos have spooled down!", Toast.LENGTH_LONG).show();

            }
        }.start();

        countdownProgressBar.setProgress(100, true);

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
