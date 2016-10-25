package com.example.kevin.senku;

import android.os.Bundle;
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

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_senku);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                child.setOnTouchListener(touchListener);
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
                            if (table.isFinished()) {
                                ((Chronometer) findViewById(R.id.varTime)).stop();
                                CharSequence moves = ((TextView) findViewById(R.id.varMoves)).getText();
                                CharSequence time = ((Chronometer) findViewById(R.id.varTime)).getText();
                                Toast.makeText(v.getContext(), "Senku completado en " + moves + " movimientos y tiempo: " + time, Toast.LENGTH_LONG).show();

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
}
