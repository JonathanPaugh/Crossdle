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
import com.example.crossdle.game.BoardGenerator;

import java.io.IOException;

public class HistoryActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private FragmentStateAdapter adapter;

    private HistoryItem[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        char[][] layout = null;
        try {
            layout = BoardGenerator.returnBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }

        items = new HistoryItem[] {
            new HistoryItem("#247", 152345, 3, 9, Board.TEST_LAYOUT),
            new HistoryItem("#246", 375229, 4, 17, layout)
        };

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