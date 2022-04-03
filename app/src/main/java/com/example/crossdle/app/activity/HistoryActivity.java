package com.example.crossdle.app.activity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.example.crossdle.R;
import com.example.crossdle.app.HistoryItem;
import com.example.crossdle.app.fragment.HistoryItemFragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;


import java.util.ArrayList;


public class HistoryActivity extends FragmentActivity {
    private ViewPager2 viewPager;
    private static final ArrayList<HistoryItem> items = new ArrayList<>();
    private static int boardCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateBoardCount();
        setContentView(R.layout.activity_history);
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

        public void setBoardCount(int value) { boardCount = value; }
    }

        public static void getCompleteHistoryDataBase(){
        for(int i = 1; i<=boardCount; i++){
            System.out.println("i"+i);
            readBoardFromDataBase(String.valueOf(i));
        }
        }

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
                    }
                }
            });
        }
    }

