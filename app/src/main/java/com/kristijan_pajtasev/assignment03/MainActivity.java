package com.kristijan_pajtasev.assignment03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private boolean isStarted = false;
    private Button startStopButton;

    private OnClickListener startStopButtonHandler = new OnClickListener() {
        @Override
        public void onClick(View v) {
            isStarted = !isStarted;
            if(isStarted) {
                startStopButton.setText(R.string.stop);
            } else {
                startStopButton.setText(R.string.start);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startStopButton = findViewById(R.id.startStopButton);

        startStopButton.setOnClickListener(startStopButtonHandler);
    }

}
