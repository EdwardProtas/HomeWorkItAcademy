package com.example.mp3player;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{

    private ArrayList<Song> songArrayList;
    private SelectedSongClickListener selectedSongClickListener;
    private MediaPlayer mediaPlayer;

    public SongAdapter(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;

    }


    public ArrayList<Song> getSongArrayList() {
        return songArrayList;
    }

    public void setSongArrayList(ArrayList<Song> songArrayList) {
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(songArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return (songArrayList != null) ? songArrayList.size() : 0;
    }

    interface SelectedSongClickListener{
        void selectedSongClickListener(Song song);
    }

    public void setSelectedSongClickListener(SelectedSongClickListener selectedSongClickListener) {
        this.selectedSongClickListener = selectedSongClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView note;
        private ImageView playerStatus;
        private TextView songText;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            songText = itemView.findViewById(R.id.songText);
            playerStatus = itemView.findViewById(R.id.playerStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Song song = songArrayList.get(getAdapterPosition());
                    song.setStatus(true);
                    playerStatus.setVisibility(View.VISIBLE);
                    selectedSongClickListener.selectedSongClickListener(song);
                }
            });
        }

        void bind(Song song){
            songText.setText(song.getName());
            playerStatus.setVisibility((song.isStatus())? View.VISIBLE : View.INVISIBLE);
        }
    }
}
