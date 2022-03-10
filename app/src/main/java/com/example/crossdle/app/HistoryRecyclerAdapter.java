package com.example.crossdle.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crossdle.R;
import com.example.crossdle.app.fragment.BoardFragment;
import com.example.crossdle.game.Board;

import java.time.LocalTime;


public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {

    private HistoryItem[] data;

    public HistoryRecyclerAdapter(HistoryItem[] data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history, viewGroup, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        HistoryItem item = data[position];
        viewHolder.setTime(item.getTime());
        viewHolder.setWords(item.getWords());
        viewHolder.setAttempts(item.getAttempts());
        viewHolder.setPreview(item.getContext(), item.getBoard());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeView;
        private final TextView wordsView;
        private final TextView attemptsView;

        public ViewHolder(View view) {
            super(view);
            timeView = view.findViewById(R.id.historyItem_textView_time);
            wordsView = view.findViewById(R.id.historyItem_textView_words);
            attemptsView = view.findViewById(R.id.historyItem_textView_attempts);
        }

        public void setTime(LocalTime time) {
            timeView.setText(time.toString());
        }
        public void setWords(int words) {
            wordsView.setText(Integer.toString(words));
        }
        public void setAttempts(int attempts) {
            attemptsView.setText(Integer.toString(attempts));
        }
        public void setPreview(AppCompatActivity activity, Board board) {
            BoardFragment.displayFragment(activity, R.id.historyItem_fragmentView_preview, board);
        }
    }
}