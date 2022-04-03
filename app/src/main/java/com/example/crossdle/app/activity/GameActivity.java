package com.example.crossdle.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.crossdle.app.HistoryItem;
import com.example.crossdle.app.fragment.BoardFragment;
import com.example.crossdle.R;
import com.example.crossdle.app.fragment.KeyboardFragment;
import com.example.crossdle.app.popup.FinishedGamePopup;
import com.example.crossdle.game.Board;
import com.example.crossdle.game.BoardView;
import com.example.crossdle.game.Cell;
import com.example.crossdle.game.RandomBoardGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameActivity extends AppCompatActivity {
    private Board board;
    private BoardFragment boardFragment;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static int attempts;
    private static GameActivity thisActivity;
    private long startTime;
    private String timeTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView view = new BoardView();

        try {
            board = new Board(view, RandomBoardGenerator.returnBoard());
        } catch (IOException e) {
            e.printStackTrace();
        }

        thisActivity = this;
        attempts = 20;

        KeyboardFragment keyboardFragment = KeyboardFragment.frame(getSupportFragmentManager(), R.id.game_fragmentView_keyboard, board);
        boardFragment = BoardFragment.frame(getSupportFragmentManager(), R.id.game_fragmentView_board, board);

        board.setOnWin(this::win);
        board.setOnLose(this::lose);

        view.setViewHandler(boardFragment::getView);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.game_song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        startTime = System.currentTimeMillis();
    }

    public void win() {
        writeBoardtoDatabase();
        int duration = 2000;

        View view = boardFragment.getView();
        animateWin(view, duration);

        view.postDelayed(() -> {
            Intent intent = new Intent(this, FinishedGamePopup.class);
            intent.putExtra("time_taken",timeTaken);
            intent.putExtra("attempts_taken",String.valueOf(20-attempts));
            intent.putExtra("title","Well Done!");
            startActivity(intent);
        }, (long)(duration * 0.7));

    }

    public void lose() {
        int duration = 2000;
        View view = boardFragment.getView();
        animateLose(view, duration);

        view.postDelayed(() -> {
            Intent intent = new Intent(this, FinishedGamePopup.class);
            intent.putExtra("time_taken",timeTaken);
            intent.putExtra("attempts_taken",String.valueOf(20-attempts));
            intent.putExtra("title","Maybe Next Time!");
            startActivity(intent);
        }, (long)(duration * 0.7));
        writeBoardtoDatabase();
    }

    private void animateWin(View view, int duration) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.mixed_anim);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void animateLose(View view, int duration) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.mixed_anim);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
    private List<String> retrieveBoard(){
        Cell[][] playerBoard = board.getBoard();
        char[][] playerGuesses = new char[playerBoard.length][playerBoard[0].length];
        for (int y = 0; y < playerBoard.length; y++) {
            System.out.println();
            for (int x = 0; x < playerBoard[y].length; x++) {
                playerGuesses[y][x] = playerBoard[y][x].getData();
            }
        }
        return Board.charToList(playerGuesses);
    }

    public void writeBoardtoDatabase() {
        long gameLength = System.currentTimeMillis() - startTime;
        timeTaken = String.valueOf(gameLength/1000.0);
        DocumentReference historyRef = db.collection("history").document(user.getUid());
        Map<String, Object> count = new HashMap<>();
        count.put("board_count", FieldValue.increment(1));
        if(20-attempts<20){
            count.put("streak", FieldValue.increment(1));
            count.put("wins", FieldValue.increment(1));
        }else{
            historyRef.update("streak", 0);
        }

        historyRef.set(count, SetOptions.merge());
        Cell[][] charArr = board.getBoard();
        List<String> list = retrieveBoard();
        int correctLetters = correctLetters(list,Board.cellToList(charArr));
        historyRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    String boardCount = (String.valueOf(document.get(("board_count"))));
                    HistoryItem history = new HistoryItem(boardCount, timeTaken, correctLetters, 20-attempts, list, Board.cellToList(charArr));
                    historyRef.collection(boardCount)
                            .document(boardCount)
                            .set(history)
                            .addOnSuccessListener(aVoid -> Log.d("W", "DocumentSnapshot successfully written!"))
                            .addOnFailureListener(e -> Log.w("W", "Error writing document", e));
                } else {
                    Log.d("W", "No such document");
                }

            } else {
                Log.d("W", "get failed with ", task.getException());
            }
        });
    }

    public static void updateAttempts(){
        attempts -= 1;
        System.out.println(attempts);
        TextView remainingView = thisActivity.findViewById(R.id.game_textView_remaining);
        remainingView.setText(String.valueOf(attempts));
    }

    public static int correctLetters(List<String> list1, List<String> list2){
        int count = 0;
       for(int i = 0;i<list1.size();i++){
           if(list1.get(i).equals(list2.get(i))){
               count+=1;
           }
       }
        return count;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
         if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                board.clickBack();
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                board.clickEnter();
                return true;
            } else {
                char character = (char)event.getUnicodeChar(event.getMetaState());
                if (Character.isLetter(character)) {
                    board.clickKey((Character.toUpperCase(character)));
                }
            }
        }

        return super.dispatchKeyEvent(event);
    }

}