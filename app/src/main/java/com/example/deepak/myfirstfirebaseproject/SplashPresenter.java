package com.example.deepak.myfirstfirebaseproject;


import com.example.deepak.myfirstfirebaseproject.CallActivityInterface;

public class SplashPresenter {
    final int WAIT_TIME = 2000;
    private CallActivityInterface callActivityInterface;

    public SplashPresenter(CallActivityInterface callActivityInterface) {
        this.callActivityInterface = callActivityInterface;
    }

    public void holdScreen(final CallActivityInterface CallActivityInterface) {
        //To hold the splash screen for two seconds
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    CallActivityInterface.callActivity();
                }
            }
        };
        timerThread.start();
    }
}
