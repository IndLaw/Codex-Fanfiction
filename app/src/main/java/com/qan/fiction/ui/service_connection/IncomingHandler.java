package com.qan.fiction.ui.service_connection;

import android.os.Handler;
import android.os.Message;

class IncomingHandler extends Handler {
    private final ConnectionManager.OnMessageReceivedListener listener;

    public IncomingHandler(ConnectionManager.OnMessageReceivedListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        listener.onReceive(msg);
    }
}
