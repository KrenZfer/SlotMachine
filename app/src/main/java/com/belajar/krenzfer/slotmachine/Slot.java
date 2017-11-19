package com.belajar.krenzfer.slotmachine;

import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by KrenZfer on 04/12/2016.
 */

public class Slot {

    Thread t;
    boolean roll;
    int slotPos,speedRoll;
    double slotindex;
    ImageView img;
    int pos[] = {-400, -225, -110, 5, 120, 225, 350};

    public Slot(int speedRoll, ImageView img){
        roll = false;
        this.speedRoll = speedRoll;
        this.img = img;
//        slotindex = Math.random() * 7;
        slotindex = 0;
        this.slotPos = pos[(int) slotindex];
        img.setScrollY(this.slotPos);
    }

    public void createThreadRollEach(final ImageView slot){
        final Handler h = new Handler();
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (roll) {
                    if(t.isInterrupted()){
                        return;
                    }
                    slotPos += speedRoll;
                    if(slotPos>pos[pos.length-1]){
                        slotPos = pos[pos.length-1];
                    }
                    if (slotPos == pos[pos.length-1]) {
                        slotPos = pos[0];
                    }
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            slot.setScrollY(slotPos);
                        }
                    });
                    try {
                        Thread.sleep(1);
                    }catch (InterruptedException e){
                        return;
                    }
                }
            }
        });
    }

    public int chooseImg(){
        int min = Math.abs(slotPos-pos[0]);
        int idx = 0;
        for (int i=1;i<pos.length;i++){
            if(min>Math.abs(slotPos-pos[i])){
                idx = i;
                min = Math.abs(slotPos-pos[i]);
            }
        }
        return pos[idx];
    }

    public void threadStart(){
        roll = true;
        createThreadRollEach(img);
        t.start();
    }

    public void threadInterrupt(){
        roll = false;
        t.interrupt();
        slotPos = chooseImg();
        img.setScrollY(slotPos);
    }

    public boolean isRoll() {
        return roll;
    }

    public int getSpeedRoll() {
        return speedRoll;
    }

    public void setSpeedRoll(int speedRoll) {
        this.speedRoll = speedRoll;
    }
}
