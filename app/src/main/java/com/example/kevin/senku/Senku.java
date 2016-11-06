package com.example.kevin.senku;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Senku extends AppCompatActivity {

    Bundle pBundle = new Bundle();
    boolean first = true;
    SenkuTable table = new SenkuTable();
    int moves = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        findViewById(R.id.btnReset).setOnClickListener(btnResetClickListener);
        initButtons();
    }

    private void initButtons() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_senku);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                child.setOnTouchListener(touchListener);
                if (child.getTag().equals("3-3")) {
                    ((RadioButton) child).setChecked(false);
                } else {
                    ((RadioButton) child).setChecked(true);
                }
            }
        }
        ((Chronometer) findViewById(R.id.varTime)).start();
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (first) {
                        pBundle.putInt("rbID", v.getId());
                        first = false;
                    } else {
                        int srcX = getXcol((RadioButton) findViewById(pBundle.getInt("rbID")));
                        int srcY = getYcol((RadioButton) findViewById(pBundle.getInt("rbID")));
                        int destX = getXcol((RadioButton) v);
                        int destY = getYcol((RadioButton) v);
                        if (table.isValidMove(srcX, srcY, destX, destY)) {
                            ((RadioButton) v).setChecked(true);
                            ((RadioButton) findViewById(pBundle.getInt("rbID"))).setChecked(false);
                            String midTag = table.move(srcX, srcY, destX, destY);
                            ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.activity_senku);
                            RadioButton mid = ((RadioButton) layout.findViewWithTag(midTag));
                            mid.setChecked(false);
                            ((TextView) findViewById(R.id.varMoves)).setText(String.valueOf(++moves));
                            if (table.isFinished() == SenkuTable.COMPLETED_WIN) {
                                endSenku(true);
                            } else if (table.isFinished() == SenkuTable.COMPLETED_LOSE) {
                                endSenku(false);
                            }
                        } else {
                            Toast.makeText(v.getContext(), "Movimiento no válido", Toast.LENGTH_SHORT).show();
                        }
                        first = true;
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
            }
            return true;
        }
    };

    private int getXcol(RadioButton rb) {
        return Integer.parseInt(((String) rb.getTag()).split("-")[0]);
    }

    private int getYcol(RadioButton rb) {
        return Integer.parseInt(((String) rb.getTag()).split("-")[1]);
    }

    private void endSenku(boolean win) {
        ((Chronometer) findViewById(R.id.varTime)).stop();
        CharSequence moves = ((TextView) findViewById(R.id.varMoves)).getText();
        CharSequence time = ((Chronometer) findViewById(R.id.varTime)).getText();
        String msg;
        if (win) {
            msg = "Enhorabuena! Senku completado en " + moves + " movimientos y tiempo: " + time;
        } else {
            msg = "Lástima, la última pieza debe quedar en la posición central.";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private View.OnClickListener btnResetClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            table = new SenkuTable();
            initButtons();
            moves = 0;
            ((TextView) findViewById(R.id.varMoves)).setText(String.valueOf(moves));
            ((Chronometer) findViewById(R.id.varTime)).setBase(SystemClock.elapsedRealtime());
            //((Chronometer) findViewById(R.id.varTime)).start();
        }
    };
}
