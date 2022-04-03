package com.example.crossdle.game;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.crossdle.R;

import java.io.Serializable;
import java.util.function.Supplier;

public class BoardView implements Serializable {
    private transient Supplier<View> viewHandler;

    private static final int[][] LAYOUT = new int[][]
    {
        { R.id.board_cell_0_0, R.id.board_cell_1_0, R.id.board_cell_2_0, R.id.board_cell_3_0, R.id.board_cell_4_0, R.id.board_cell_5_0, },
        { R.id.board_cell_0_1, R.id.board_cell_1_1, R.id.board_cell_2_1, R.id.board_cell_3_1, R.id.board_cell_4_1, R.id.board_cell_5_1, },
        { R.id.board_cell_0_2, R.id.board_cell_1_2, R.id.board_cell_2_2, R.id.board_cell_3_2, R.id.board_cell_4_2, R.id.board_cell_5_2, },
        { R.id.board_cell_0_3, R.id.board_cell_1_3, R.id.board_cell_2_3, R.id.board_cell_3_3, R.id.board_cell_4_3, R.id.board_cell_5_3, },
        { R.id.board_cell_0_4, R.id.board_cell_1_4, R.id.board_cell_2_4, R.id.board_cell_3_4, R.id.board_cell_4_4, R.id.board_cell_5_4, },
        { R.id.board_cell_0_5, R.id.board_cell_1_5, R.id.board_cell_2_5, R.id.board_cell_3_5, R.id.board_cell_4_5, R.id.board_cell_5_5, }
    };

    public void setViewHandler(Supplier<View> getView) {
        this.viewHandler = getView;
    }

    public void draw(Cell[][] cells) {
        View view = viewHandler.get();
        for (int y = 0; y < LAYOUT.length; y++) {
            for (int x = 0; x < LAYOUT[y].length; x++) {
                Cell cell = cells[y][x];
                cell.draw(view.findViewById(LAYOUT[y][x]));
            }
        }
    }

    public void animateCell(Cell cell) {
        View view = viewHandler.get();
        cell.animate(view.findViewById(LAYOUT[cell.getY()][cell.getX()]));
    }
}
