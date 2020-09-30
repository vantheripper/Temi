package com.example.temi_test2;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
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
import android.widget.VideoView;

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
            mSocket = IO.socket("http://192.168.50.65:4000");

        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temisdk_location_row);
        final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
//        temiSDK.speak("Hello");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        mSocket.connect();
        mSocket.on("robot_gofront2", Forward);
        mSocket.on("robot_goleft", Spin_L);
        mSocket.on("robot_goright", Spin_R);
        mSocket.on("robot_stop", Stop);
        mSocket.on("robot_savea", Go_A);
        mSocket.on("robot_saveb", Go_B);
        mSocket.on("robot_spin", Spin_180);
        mSocket.on("robot_welcome", Play_welcome);
        mSocket.on("robot_face", Play_face);
        mSocket.on("robot_Home", Go_Home);
        mSocket.on("robot_thank_ns", Play_thank_temi);
        mSocket.on("robot_thank", Play_thank);
        mSocket.on("robot_welcome_ns", Play_welcome_temi);
        mSocket.on("robot_tilt", Tilt);
        mSocket.on("robot_dance", dance);
        Log.d("myTag", "8888888888888888888");
//        button = (Button) findViewById(R.id.button_speker);
//        stop = (Button) findViewById(R.id.button_stop);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // temiSDK.speak("ทดสอบ สวัสดีท่าสมาชิก");
//
//                temiSDK.turnBy(180);
//                //temiSDK.skidJoy();
//              /* new CountDownTimer(1000, 1000) {
//
//                    public void onTick(long millisUntilFinished) {
//
//                    }
//
//                    public void onFinish() {
//                       temiSDK.stopMovement();
//                    }
//                }.start();*/
//                //Play from file
//             /*   mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.victim);
//                mediaPlayer.start(); */
//                //temiSDK.tiltAngle(23);
//               // Log.d("myTag", "This is my message");
//            }
//        });
//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                temiSDK.goTo("c");
//                //temiSDK.tiltAngle(18);
//                //temiSDK.turnBy(-45);
//                //temiSDK.stopMovement();
//               // mediaPlayer.stop();
//            }
//        });
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
                    temiSDK.turnBy(15);

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
                    temiSDK.turnBy(-15);

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
                    temiSDK.goTo("a",false);

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
                    temiSDK.goTo("b",false);
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
    private Emitter.Listener Play_welcome = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "play video");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    //play video

                    playWelcome();

                    //temiSDK.speak("Hi i am Temi, Nice to meet you. I have a cool academic Engineering program for you to see. Please take a look at our new brochure.",false);

                }
            });
        }
    };
    private Emitter.Listener Play_welcome_temi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "play video");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    //play video
                    playWelcome_temi();
                    temiSDK.speak("Hi i am Temi, Nice to meet you. I have a cool academic Engineering program for you to see. Please take a look at our new brochure.",false);

                }
            });
        }
    };
    private Emitter.Listener Play_thank = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "play video");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());


                    //play video

                    //temiSDK.speak("Thank you so much. I'm very happy to talk with you today. Hope to see you again.",false);
                    playThank();
                }
            });
        }
    };
    private Emitter.Listener Play_thank_temi = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "play video");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());


                    //play video
                    playThank_temi();
                    temiSDK.speak("Thank you so much. I'm very happy to talk with you today. Hope to see you again.",false);

                }
            });
        }
    };
    private Emitter.Listener Go_Home = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "go Home");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());


                    //play video
                    temiSDK.goTo(TemiSDK.HOME_BASE_LOCATION);

                }
            });
        }
    };
    private Emitter.Listener  Tilt = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "go Home");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    temiSDK.tiltAngle(23);


                    new CountDownTimer(1000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        temiSDK.tiltAngle(55);
                    }
                }.start();

                }
            });
        }
    };
    private Emitter.Listener  dance = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "go Home");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());


                    temiSDK.turnBy(360);




                }
            });
        }
    };
    private Emitter.Listener Play_face = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("myTag", "play face");
                    final TemiSDK temiSDK = new TemiSDK(getApplicationContext());
                    //play face
                    runUI();




                }
            });
        }
    };

    private void runUI(){
        final VideoView vv = (VideoView) findViewById(R.id.videoView2);

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + +R.raw.step10);
        vv.setVideoURI(uri);
        vv.requestFocus();
        vv.start();
    }

    private void playWelcome() {
        final VideoView videoView = (VideoView) findViewById(R.id.videoView2);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer mp) { mp.setLooping(false);
                                            }
                                        });


                String path = "android.resource://" + getPackageName() + "/" + R.raw.welcome_s;
        videoView.setVideoPath(path);
        videoView.start();
    }
    private void playWelcome_temi() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView2);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) { mp.setLooping(false);
            }
        });
        String path = "android.resource://" + getPackageName() + "/" + R.raw.welcome_ns;
        videoView.setVideoPath(path);
        videoView.start();
    }
    private void playThank() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView2);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) { mp.setLooping(false);
            }
        });
        String path = "android.resource://" + getPackageName() + "/" + R.raw.thank_s;
        videoView.setVideoPath(path);
        videoView.start();
    }
    private void playThank_temi() {
        VideoView videoView = (VideoView) findViewById(R.id.videoView2);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) { mp.setLooping(false);
            }
        });
        String path = "android.resource://" + getPackageName() + "/" + R.raw.thank_ns;
        videoView.setVideoPath(path);
        videoView.start();
    }

}