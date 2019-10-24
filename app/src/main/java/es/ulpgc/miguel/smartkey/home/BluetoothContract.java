package es.ulpgc.miguel.smartkey.home;

import android.bluetooth.BluetoothSocket;

public interface BluetoothContract {
  interface ConnectThread {
    void onSocketConnected(BluetoothSocket socket);
  }
}
