package es.ulpgc.miguel.smartkey.services.bluetooth;

import android.bluetooth.BluetoothSocket;

public interface BluetoothContract {
  interface ConnectThread {
    void onSocketConnected(BluetoothSocket socket);
  }
}
