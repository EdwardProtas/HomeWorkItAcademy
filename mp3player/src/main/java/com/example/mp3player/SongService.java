package com.example.mp3player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class SongService extends Service {

    private MediaPlayer mediaPlayer;
    private Song songPosition;
    private final IBinder mediaBind = new MediaBinder();
    private FollowwingMedia followwingMedia;
    private PreviousMedia previousMedia;
    public static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_STOP = "ACTION_STOP";

    interface FollowwingMedia {
        void following(Song song);
    }

    interface PreviousMedia {
        void previous(Song song);
    }

    public FollowwingMedia getFollowwingMedia() {
        return followwingMedia;
    }

    public void setFollowwingMedia(FollowwingMedia followwingMedia) {
        this.followwingMedia = followwingMedia;
    }

    public PreviousMedia getPreviousMedia() {
        return previousMedia;
    }

    public void setPreviousMedia(PreviousMedia previousMedia) {
        this.previousMedia = previousMedia;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case ACTION_PLAY:

                    break;
                case ACTION_PREVIOUS:
                    previousMedia.previous(songPosition);
                    break;
                case ACTION_NEXT:
                    followwingMedia.following(songPosition);
                    break;
                case ACTION_STOP:
                    mediaPlayer.stop();
                    stopSelf();
                    break;
                default:
                    break;
            }
        }
        Song song = (Song) intent.getSerializableExtra("Song");
        if (song != null) {
            Notification notification = getNotification(song);
            startForeground(1, notification);
            startPlayer(song);
            songPosition = song;
        }
        return START_REDELIVER_INTENT;
    }


    private void startPlayer(final Song song) {
        if (mediaPlayer != null) mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, song.getNumber());
        mediaPlayer.start();
    }

    private Notification getNotification(Song song) {

        return new NotificationCompat
                .Builder(this, MainActivity.getChannelId())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(song.getName())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_music_note_black_24dp)
                .setContentIntent(openPlayer())
                .addAction(R.drawable.ic_skip_previous_black_24dp, getString(R.string.previous), prevPlayer())
                .addAction(R.drawable.ic_pause_black_24dp, getString(R.string.stop) ,stopPlayer())
                .addAction(R.drawable.ic_skip_next_black_24dp, getString(R.string.next), nextPlayer())
                .build();
    }

    public PendingIntent stopPlayer() {
        Intent stop = new Intent(this, SongService.class);
        stop.setAction(ACTION_STOP);
        return PendingIntent.getService(this, 4, stop, 0);
    }

    public PendingIntent prevPlayer() {
        Intent prev = new Intent(this, SongService.class);
        prev.setAction(ACTION_PREVIOUS);
        return PendingIntent.getService(this, 3, prev, 0);
    }

    public PendingIntent nextPlayer() {
        Intent next = new Intent(this, SongService.class);
        next.setAction(ACTION_NEXT);
        return PendingIntent.getService(this, 2, next, 0);
    }

    public PendingIntent playPlayer() {
        Intent next = new Intent(this, SongService.class);
        next.setAction(ACTION_PLAY);
        return PendingIntent.getService(this, 1, next, 0);
    }

    public PendingIntent openPlayer() {
        Intent openPlayer = new Intent(this, MainActivity.class);
        openPlayer.putExtra("Song", songPosition);
        openPlayer.setAction(ACTION_PLAY);
        return PendingIntent.getActivity(this, 0, openPlayer, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MediaBinder();
    }


    public class MediaBinder extends Binder {
        SongService getService() {
            return SongService.this;
        }
    }

}
