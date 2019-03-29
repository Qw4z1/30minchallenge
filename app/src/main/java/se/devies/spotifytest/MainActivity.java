package se.devies.spotifytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private ButtonState state = ButtonState.START;

    private Timer timer = new Timer();

    private Random random = new Random();

    private Long startTime = 0L;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextState();
            }
        });

    }

    private void startTimer() {
        setButtonState(ButtonState.HOLD);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setButtonState(ButtonState.GO);
                        startTime = System.currentTimeMillis();
                    }
                });
            }
        }, getTimeout());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setButtonState(ButtonState.START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    private void nextState() {
        if (state == ButtonState.START) {
            startTimer();
        } else if (state == ButtonState.GO) {
            evaluate();
        } else if (state == ButtonState.HOLD) {
            fail();
        }
    }

    private void evaluate() {
        long difference = System.currentTimeMillis() - startTime;
        textView.setText(String.valueOf(difference));
        setButtonState(ButtonState.START);
    }

    private void fail() {
        timer.cancel();
        Toast.makeText(this, R.string.patience, Toast.LENGTH_SHORT).show();
        setButtonState(ButtonState.START);
    }

    private long getTimeout() {
        return random.nextInt(4000) + 1000;
    }

    private void setButtonState(ButtonState state) {
        button.setText(state.text);
        this.state = state;
    }
}
