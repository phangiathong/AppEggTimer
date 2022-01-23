package com.academy.m6_appeggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timesSeekBar;
    TextView txtDisplay,txtTimer;
    boolean counterIsActive=false;
    Button btnGo;
    CountDownTimer countDownTimer;

    public void updateTimer(int progress){

        int minutes=progress/60;
        int seconds=progress - (minutes*60);

        String secondString=Integer.toString(seconds);

        if (seconds<=9){
            secondString="0"+seconds;
        }

        txtTimer.setText(minutes+":"+secondString);

    }

    public void resetTime(){
        counterIsActive=false;
        btnGo.setText("Go!");
        txtTimer.setText("0:30");
        timesSeekBar.setEnabled(true);
        timesSeekBar.setProgress(30);
        countDownTimer.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timesSeekBar=findViewById(R.id.timerSeekbar);
        txtDisplay=findViewById(R.id.txtDisplay);
        txtTimer=findViewById(R.id.txtTimer);
        btnGo=findViewById(R.id.btnGo);

        timesSeekBar.setMax(600);
        timesSeekBar.setProgress(30);

        timesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        txtDisplay.setVisibility(View.INVISIBLE);
    }

    public void btnGo(View view) {

        if (counterIsActive){

            resetTime();

        }else {

            counterIsActive=true;
            btnGo.setText("Stop");
            timesSeekBar.setEnabled(false);
            txtDisplay.setVisibility(View.INVISIBLE);

            countDownTimer=new CountDownTimer(timesSeekBar.getProgress()*1000,1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int)millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {

                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mediaPlayer.start();
                    txtDisplay.setVisibility(View.VISIBLE);
                    resetTime();
                }
            }.start();
        }



    }
}