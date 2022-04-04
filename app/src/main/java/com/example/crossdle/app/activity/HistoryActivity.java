package com.example.crossdle.app.activity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.crossdle.R;
import com.example.crossdle.app.HistoryItem;
import com.example.crossdle.app.fragment.HistoryItemFragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.text.DecimalFormat;
import java.util.ArrayList;


public class HistoryActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private static final ArrayList<HistoryItem> items = new ArrayList<>();
    private static int boardCount;
    private static int winCount;
    private static int streakCount;
    private TextView gameView;
    private TextView streakView;
    private TextView winView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        gameView = findViewById(R.id.history_textView_games);
        streakView = findViewById(R.id.history_textView_streak);
        winView = findViewById(R.id.history_textView_win);
        FragmentStateAdapter adapter = new HistoryPagerAdapter(this);
        viewPager = findViewById(R.id.history_viewPager);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameView.setText(String.valueOf(boardCount));
        streakView.setText(String.valueOf(streakCount));
        String winPercent = String.format("%.2f",winCount*100.0/(boardCount))+"%";
        winView.setText(winPercent);
        FragmentStateAdapter adapter = new HistoryPagerAdapter(this);
        viewPager = findViewById(R.id.history_viewPager);
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

    private static class HistoryPagerAdapter extends FragmentStateAdapter {

        public HistoryPagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            return HistoryItemFragment.newInstance(items.get(position));
        }

        @Override
        public int getItemCount() {
            return boardCount;
        }

    }

        public static void getCompleteHistoryDataBase(){
        for(int i = 1; i<=boardCount; i++){
            System.out.println("i"+i);
            readBoardFromDataBase(String.valueOf(i));
        }
        }

        public int getBoardCount() {return boardCount;}
        public int getWinCount() {return winCount;}
        public int getStreakCount() {return streakCount;}

        public static void readBoardFromDataBase(String id) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference docRef = db.collection("history").document(user.getUid());
            docRef.collection(id).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("W", document.getId() + " => " + document.getData());
                        HistoryItem history = document.toObject(HistoryItem.class);
                        items.add(history);
                    }
                } else {
                    Log.d("W", "Error getting documents: ", task.getException());
                }
            });
        }


        public static void updateBoardCount(){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference historyRef = db.collection("history").document(user.getUid());
            historyRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        boardCount = Integer.parseInt(String.valueOf(document.get(("board_count"))));
                        System.out.println("here1"+boardCount);
                        streakCount = Integer.parseInt(String.valueOf(document.get(("streak"))));
                        System.out.println("here2"+streakCount);
                        winCount = Integer.parseInt(String.valueOf(document.get(("wins"))));
                        System.out.println("here3"+winCount);
                    }
                }
            });
        }
    }

