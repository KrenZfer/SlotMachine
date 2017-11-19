package com.belajar.krenzfer.slotmachine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    ImageView slot1, slot2, slot3;
    TextView judgetxt;
    Button btnmulai, btnstop;
    int speedRoll;
    Slot[] slots;
    int counterClick = 0;
    String[] image = {"Lucky 7","Delicious Watermelon", "Shiny Diamond", "Perfect Gold Clover", "Precious Bar", "Golden Bell"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slot1 = (ImageView) findViewById(R.id.slot1);
        slot2 = (ImageView) findViewById(R.id.slot2);
        slot3 = (ImageView) findViewById(R.id.slot3);

        judgetxt = (TextView) findViewById(R.id.judge);

        btnmulai = (Button) findViewById(R.id.mulai);
        btnstop = (Button) findViewById(R.id.stop);

        slots = new Slot[3];
        speedRoll = 3;

        slots[0] = new Slot(speedRoll,slot1);
        slots[1] = new Slot(speedRoll,slot2);
        slots[2] = new Slot(speedRoll,slot3);
    }

    public void mulai_slot(View v) {
        judgetxt.setText("");
        if(!slots[0].isRoll()&&!slots[1].isRoll()&&!slots[2].isRoll()){
            slots[0].threadStart();
            slots[1].threadStart();
            slots[2].threadStart();
        }
    }

    public void stop_slot(View v) {
        if(slots[0].isRoll()||slots[1].isRoll()||slots[2].isRoll()){
            counterClick++;
            if(counterClick%3==1){
                slots[0].threadInterrupt();
            }
            if(counterClick%3==2){
                slots[1].threadInterrupt();
            }
            if(counterClick%3==0){
                slots[2].threadInterrupt();
                judgementSlot();
                counterClick = 0;
            }
        }
    }

    public void judgementSlot(){
        int value[] = new int[3];
        boolean judge = true;
        for (int i = 0;i<slots.length;i++){
            for (int j = 0;j<slots[i].pos.length;j++){
                if(slots[i].slotPos==slots[i].pos[j]){
                    value[i] = j;
                }
            }
        }
        for (int i = 0;i<value.length-1;i++){
            if(value[i]!=value[i+1]){
                judge = false;
                break;
            }
        }
        if(judge){
            judgetxt.setText("You Won "+image[value[0]]+"!!!!!!");
        }else{
            judgetxt.setText("You Lose :'(");
        }
    }
}
