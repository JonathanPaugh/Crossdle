package com.example.crossdle.app.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.crossdle.R;
import com.example.crossdle.app.HistoryItem;
import com.example.crossdle.app.fragment.HistoryItemFragment;
import com.example.crossdle.game.Board;

import java.time.LocalTime;

public class HistoryActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private FragmentStateAdapter adapter;

    private HistoryItem[] items = new HistoryItem[] {
        new HistoryItem(this, "#247", LocalTime.now(), 5, 10, new Board(null, Board.TEST_LAYOUT)),
        new HistoryItem(this, "#246", LocalTime.now(), 7, 9, new Board(null, Board.TEST_LAYOUT))
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        viewPager = findViewById(R.id.history_viewPager);
        adapter = new HistoryPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() <= 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class HistoryPagerAdapter extends FragmentStateAdapter {
        public HistoryPagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            return HistoryItemFragment.newInstance(items[position]);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }
    }
}