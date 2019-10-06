package es.ulpgc.miguel.smartkey.app;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import es.ulpgc.miguel.smartkey.login.LoginState;

public class AppMediator extends Application {

  // declaring the states
  private LoginState loginState;

  @Override
  public void onCreate() {
    super.onCreate();

    // initializing the FirebaseApp
    FirebaseApp.initializeApp(getApplicationContext());

    // creating states
    this.loginState = new LoginState();
  }

  public LoginState getLoginState() {
    return loginState;
  }

  public void setLoginState(LoginState loginState) {
    this.loginState = loginState;
  }
}
