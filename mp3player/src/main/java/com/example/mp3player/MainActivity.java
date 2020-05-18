package com.example.mp3player;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.SeekBar;


import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private ArrayList<Song> songArrayList = new ArrayList<>();
    private SongService songService;
    private Intent intent;
    private boolean mediaBound = false;
    private SeekBar seekBar;
    private Handler mHandler = new Handler();
    private Runnable runnable;

    public static String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        seekBar = findViewById(R.id.seekBar);
        addSongList();
        initRecyclerView();
        createNotification();
        touchSongAdapter();
        playCycle();
    }

    public void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void addSongList() {
        songArrayList.add(new Song("Song 1", R.raw.bool_never_go));
        songArrayList.add(new Song("Song 2", R.raw.boostee_pop_corn));
        songArrayList.add(new Song("Song 3", R.raw.starley_call_on_me));
        songArrayList.add(new Song("Song 4", R.raw.oceana_can_stop_thinking_about_you));

    }

    public void initRecyclerView() {
        recyclerView.setAdapter(new SongAdapter(songArrayList));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        songAdapter = (SongAdapter) recyclerView.getAdapter();
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            SongService.MediaBinder binder = (SongService.MediaBinder) iBinder;
            songService = binder.getService();
            nextSongClick();
            previousSongClick();
            mediaBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mediaBound = false;
        }
    };

    public void touchSongAdapter() {
        songAdapter.setSelectedSongClickListener(new SongAdapter.SelectedSongClickListener() {
            @Override
            public void selectedSongClickListener(Song song) {
                if (!mediaBound) {
                    intent = new Intent(MainActivity.this, SongService.class);
                    intent.putExtra("Song", song);
                    ContextCompat.startForegroundService(MainActivity.this, intent);
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        songService = null;
        mHandler.removeCallbacks(runnable);
        super.onDestroy();
    }


    public void playCycle() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (songService != null && songService.getMediaPlayer() != null) {
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                seekBar.setMax(songService.getMediaPlayer().getDuration());
                                seekBar.setProgress(songService.getMediaPlayer().getCurrentPosition());
                            }
                        };
                        mHandler.post(runnable);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    public void nextSongClick() {
        songService.setFollowwingMedia(new SongService.FollowwingMedia() {
            @Override
            public void following(Song song) {
                for (int i = 0; i < songArrayList.size(); i++) {
                    int nextMusic = i + 1;
                    if (songArrayList.get(i).getNumber() == song.getNumber() && nextMusic < songArrayList.size()) {
                        intent = new Intent(MainActivity.this, SongService.class);
                        intent.putExtra("Song", songArrayList.get(nextMusic));
                        ContextCompat.startForegroundService(MainActivity.this, intent);
                    }
                }
            }
        });
    }

    public void previousSongClick() {
        songService.setPreviousMedia(new SongService.PreviousMedia() {
            @Override
            public void previous(Song song) {
                for (int i = 0; i < songArrayList.size(); i++) {
                    int previousMusic = i - 1;
                    if (songArrayList.get(i).getNumber() == song.getNumber() && previousMusic >= 0) {
                        intent = new Intent(MainActivity.this, SongService.class);
                        intent.putExtra("Song", songArrayList.get(previousMusic));
                        ContextCompat.startForegroundService(MainActivity.this, intent);
                    }
                }
            }
        });
    }


}