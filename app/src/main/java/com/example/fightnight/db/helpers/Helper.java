package com.example.fightnight.db.helpers;

import android.content.Context;

public abstract class Helper extends Thread{

    protected HelperListener listener;
    protected abstract void getData();
    protected Context context;

    public void run(){
        getData();
        listener.notifyListener();
    }

    public Helper(HelperListener listener, Context context){
        this.listener = listener;
        this.context = context;
    }
}
