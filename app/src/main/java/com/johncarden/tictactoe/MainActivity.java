package com.johncarden.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // 0 = yellow, 1 = red
    int activePlayer = 0;

    //2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {0,4,8},{2,4,6}};

    boolean freeze = false;
    boolean isWin = false;


    public void dropIn(View v) {
        if (freeze) {
        } else {
            ImageView counter = (ImageView) v;

            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            System.out.println(tappedCounter);

            if (gameState[tappedCounter] == 2) {

                gameState[tappedCounter] = activePlayer;
                counter.setTranslationY(-1000f);
                if (activePlayer == 0) {
                    counter.setImageResource(R.drawable.yellow);
                    activePlayer = 1;
                } else {
                    counter.setImageResource(R.drawable.red);
                    activePlayer = 0;
                }
                counter.animate().translationYBy(1000f).setDuration(250);
                String winner = "X's";
                boolean deadlocked = true;

                for (int[] winningPosition : winningPositions) {

                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                            && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                            && gameState[winningPosition[0]] != 2) {
                        isWin = true;
                        if (isWin)
                            if (gameState[winningPosition[0]] == 0)
                                winner = "O's";
                    }
                }

                if (!isWin) {
                    for (int i = 0; i < gameState.length; i++) {
                        if (gameState[i] == 2)
                            deadlocked = false;
                    }}


                    TextView winnerMessage = (TextView) findViewById(R.id.WinnerMessage);
                    if (isWin) {
                        //Someone has won !
                        winnerMessage.setText(winner + " have won!");
                        winnerMessage.setTextSize(30f);
                    } else if (deadlocked) {
                        winnerMessage.setText("The game is deadlocked");
                        winnerMessage.setTextSize(25f);
                    }
                    if (isWin || deadlocked) {
                        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        layout.setTranslationY(0f);
                        layout.animate().alpha(1f).setDuration(1000);
                        layout.bringToFront();

                        isWin = true;
                        freeze = true;


                    }


                } else {
                    Toast.makeText(this, "Please make a valid choice", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < gameState.length; i++)
                    System.out.print(gameState[i] + "  ");
                System.out.println();

            }
        }



    public void playAgain(View v) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setAlpha(0f);
        layout.setTranslationY(-1000f);
        activePlayer = 0;
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout  gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i < gridLayout.getChildCount(); i++){

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        freeze = false;
        isWin = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setTranslationY(-1000f);
    }
}
