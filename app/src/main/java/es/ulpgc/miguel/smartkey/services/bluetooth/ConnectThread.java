package es.ulpgc.miguel.smartkey.services.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
  private static final String TAG = ConnectThread.class.getSimpleName();
  private static UUID MY_UUID;
  private final BluetoothSocket mmSocket;
  private final BluetoothAdapter bluetoothAdapter;
  private final BluetoothContract.ConnectThread callback;

  public ConnectThread(BluetoothDevice device, BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothContract.ConnectThread callback) {
    // Use a temporary object that is later assigned to mmSocket
    // because mmSocket is final.
    BluetoothSocket tmp = null;

    this.MY_UUID = uuid;
    this.bluetoothAdapter = bluetoothAdapter;
    this.callback = callback;

    try {
      // Get a BluetoothSocket to connect with the given BluetoothDevice.
      // MY_UUID is the app's UUID string, also used in the server code.
      tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
    } catch (IOException e) {
      Log.e(TAG, "Socket's create() method failed", e);
    }
    mmSocket = tmp;
  }

  public void run() {
    // Cancel discovery because it otherwise slows down the connection.
    bluetoothAdapter.cancelDiscovery();

    try {
      // Connect to the remote device through the socket. This call blocks
      // until it succeeds or throws an exception.
      mmSocket.connect();
    } catch (IOException connectException) {
      // Unable to connect; close the socket and return.
      try {
        mmSocket.close();
      } catch (IOException closeException) {
        Log.e(TAG, "Could not close the client socket", closeException);
      }
      return;
    }

    // The connection attempt succeeded. Perform work associated with
    // the connection in a separate thread.
    callback.onSocketConnected(mmSocket);
    try {
      mmSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
