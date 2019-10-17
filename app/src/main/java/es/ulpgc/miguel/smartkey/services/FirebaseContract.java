package es.ulpgc.miguel.smartkey.services;

import java.util.ArrayList;

import es.ulpgc.miguel.smartkey.models.Door;

public interface FirebaseContract {

  interface LoginCallback {
    void onLoggedIn(boolean error);
  }

  interface RegisterCallback {
    void onRegistered(boolean error);
  }

  interface LogoutCallback {
    void onLoggedOut();
  }

  interface FetchDoors {
    void onDoorsFetch(ArrayList<Door> doorArrayList);
  }

  interface OpenDoor {
    void onDoorOpen(boolean error);
  }
}
