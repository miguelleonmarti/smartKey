package es.ulpgc.miguel.smartkey.services;

public interface FirebaseContract {

  interface LoginCallback {
    void onLoggedIn(boolean error);
  }

  interface RegisterCallback {
    void onRegistered(boolean error);
  }
}
