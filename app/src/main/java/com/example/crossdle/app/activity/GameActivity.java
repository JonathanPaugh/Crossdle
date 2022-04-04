package com.example.crossdle.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crossdle.app.HistoryItem;
import com.example.crossdle.app.fragment.BoardFragment;
import com.example.crossdle.R;
import com.example.crossdle.app.fragment.KeyboardFragment;
import com.example.crossdle.app.popup.FinishedGamePopup;
import com.example.crossdle.bank.WordBase;
import com.example.crossdle.game.Board;
import com.example.crossdle.game.BoardView;
import com.example.crossdle.bank.WordDictionary;
import com.example.crossdle.game.Cell;
import com.example.crossdle.game.LayoutGenerator;
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
    public static final String ARG_TYPE = "ARG_TYPE";

    private Board board;
    private BoardFragment boardFragment;
    private KeyboardFragment keyboardFragment;
    private MediaPlayer mediaPlayer;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private long startTime;
    private String timeTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        WordBase.load(this);
        WordDictionary.load(this);
        Intent intent2 = getIntent();
        String themeData = intent2.getStringExtra("theme");
        LinearLayout linearLayout = findViewById(R.id.linear_layout_game);

        if(themeData != null){
            switch (themeData){
                case "Ocean":
                    linearLayout.setBackgroundResource(R.drawable.gradient_list_ocean);
                    break;
                case "Emerald Forest":
                    linearLayout.setBackgroundResource(R.drawable.gradient_list_emerald_green);
                    break;
                case "Strawberry":
                    linearLayout.setBackgroundResource(R.drawable.gradient_list_strawberry);
                    break;
                default:
                    linearLayout.setBackgroundResource(R.drawable.gradient_list);
                    break;
            }
        }

        Intent intent = getIntent();
        boolean type = intent.getBooleanExtra(ARG_TYPE, false);

        char[][] layout = null;
        if (type) {
            try {
                layout = LayoutGenerator.returnBoard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            layout = Board.TEST_LAYOUT;
        }

        BoardView view = new BoardView();
        board = new Board(view, layout);

        keyboardFragment = KeyboardFragment.frame(getSupportFragmentManager(), R.id.game_fragmentView_keyboard, board);
        boardFragment = BoardFragment.frame(getSupportFragmentManager(), R.id.game_fragmentView_board, board);

        board.setOnWin(this::win);
        board.setOnLose(this::lose);

        view.setViewHandler(boardFragment::getView);

        startTime = System.currentTimeMillis();

        mediaPlayer = MediaPlayer.create(this, R.raw.game_song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public void win() {
        writeBoardToDatabase();

        int duration = 2000;

        View view = boardFragment.getView();
        animateWin(view, duration);

        view.postDelayed(() -> {
            startFinishedGame("You Win!");
        }, (long)(duration * 0.7));
    }

    public void lose() {
        writeBoardToDatabase();

        int duration = 2000;

        View view = boardFragment.getView();
        animateLose(view, duration);

        view.postDelayed(() -> {
            startFinishedGame("Game Over!");
        }, (long)(duration * 0.7));
    }

    private void startFinishedGame(String title) {
        Intent intent = new Intent(this, FinishedGamePopup.class);
        intent.putExtra("time_taken", timeTaken);
        intent.putExtra("attempts_taken", String.valueOf(board.getAttemptsTaken()));
        intent.putExtra("title", title);
        startActivity(intent);
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

    private List<String> retrieveBoard(){
        Cell[][] playerBoard = board.getData();
        char[][] playerGuesses = new char[playerBoard.length][playerBoard[0].length];
        for (int y = 0; y < playerBoard.length; y++) {
            System.out.println();
            for (int x = 0; x < playerBoard[y].length; x++) {
                playerGuesses[y][x] = playerBoard[y][x].getData();
            }
        }
        return Board.charToList(playerGuesses);
    }

    public void writeBoardToDatabase() {
        long gameLength = System.currentTimeMillis() - startTime;
        timeTaken = String.valueOf(gameLength/1000.0);
        DocumentReference historyRef = db.collection("history").document(user.getUid());
        Map<String, Object> count = new HashMap<>();
        count.put("board_count", FieldValue.increment(1));
        if(board.getAttemptsRemaining() > 0){
            count.put("streak", FieldValue.increment(1));
            count.put("wins", FieldValue.increment(1));
        }else{
            historyRef.update("streak", 0);
        }

        historyRef.set(count, SetOptions.merge());
        Cell[][] charArr = board.getData();
        List<String> list = retrieveBoard();
        int correctLetters = correctLetters(list,Board.cellToList(charArr));
        historyRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    String boardCount = (String.valueOf(document.get(("board_count"))));
                    HistoryItem history = new HistoryItem(boardCount, timeTaken, correctLetters, board.getAttemptsTaken(), list, Board.cellToList(charArr));
                    historyRef.collection("games")
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

    public static int correctLetters(List<String> list1, List<String> list2){
        int count = 0;
        for(int i = 0;i<list1.size();i++){
            if(list1.get(i).equals(list2.get(i))){
                count+=1;
            }
        }
        return count;
    }
}