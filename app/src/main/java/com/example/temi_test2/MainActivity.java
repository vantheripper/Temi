package com.example.temi_test2;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import androidx.appcompat.app.AppCompatActivity;
import android.os.CountDownTimer;
import android.media.AudioManager;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button stop;
    MediaPlayer mediaPlayer;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.59.154.39:4000");

        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
//        temiSDK.speak("Hello");

        mSocket.connect();
        mSocket.on("robot_gofront2", Forward);
        mSocket.on("robot_goleft", Spin_L);
        mSocket.on("robot_goright", Spin_R);
        mSocket.on("robot_stop", Stop);
        mSocket.on("robot_savea", Go_A);
        mSocket.on("robot_saveb", Go_B);
        mSocket.on("robot_spin", Spin_180);
        mSocket.on("robot_play", Play_video);
        Log.d("myTag", "8888888888888888888");
        button = (Button) findViewById(R.id.button_speker);
        stop = (Button) findViewById(R.id.button_stop);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // temiSDK.speak("ทดสอบ สวัสดีท่าสมาชิก");

                temiSDK.turnBy(180);
                //temiSDK.skidJoy();
              /* new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                       temiSDK.stopMovement();
                    }
                }.start();*/
                //Play from file
             /*   mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.victim);
                mediaPlayer.start(); */
                //temiSDK.tiltAngle(23);
               // Log.d("myTag", "This is my message");
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temiSDK.goTo("c");
                //temiSDK.tiltAngle(18);
                //temiSDK.turnBy(-45);
                //temiSDK.stopMovement();
               // mediaPlayer.stop();
            }
        });
    }
    private Emitter.Listener Forward  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Forward");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.skidJoy();

                    // add the message to view

                }
            });
        }
    };

    private Emitter.Listener Spin_L  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Left");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.turnBy(10);

                    // add the message to view

                }
            });
        }
    };
    private Emitter.Listener Spin_R  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Right");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.turnBy(-10);

                    // add the message to view

                }
            });
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
    }
    private Emitter.Listener Stop  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Right");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.stopMovement();

                    // add the message to view

                }
            });
        }
    };
    private Emitter.Listener Go_A  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Right");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.goTo("a");

                    // add the message to view

                }
            });
        }
    };
    private Emitter.Listener Go_B  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Right");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.goTo("b");

                    // add the message to view

                }
            });
        }
    };
    private Emitter.Listener Spin_180  = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Right");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.turnBy(180);

                    // add the message to view

                }
            });
        }
    };
    private Emitter.Listener Play_video = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "Spin_Right");


                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());


                    // add the message to view

                }
            });
        }
    };
}