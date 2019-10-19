package es.ulpgc.miguel.smartkey.app;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import es.ulpgc.miguel.smartkey.forgotten.ForgottenState;
import es.ulpgc.miguel.smartkey.home.HomeState;
import es.ulpgc.miguel.smartkey.profile.ProfileState;
import es.ulpgc.miguel.smartkey.register.RegisterState;
import es.ulpgc.miguel.smartkey.login.LoginState;

public class AppMediator extends Application {

  // declaring the states
  private LoginState loginState;
  private RegisterState registerState;
  private HomeState homeState;
  private ProfileState profileState;
  private ForgottenState forgottenState;

  @Override
  public void onCreate() {
    super.onCreate();

    // initializing the FirebaseApp
    FirebaseApp.initializeApp(getApplicationContext());

    // initializing states
    this.loginState = new LoginState();
    this.registerState = new RegisterState();
    this.homeState = new HomeState();
    this.profileState = new ProfileState();
    this.forgottenState = new ForgottenState();
  }

  public LoginState getLoginState() {
    return loginState;
  }

  public void setLoginState(LoginState loginState) {
    this.loginState = loginState;
  }

  public RegisterState getRegisterState() {
    return registerState;
  }

  public void setRegisterState(RegisterState registerState) {
    this.registerState = registerState;
  }

  public HomeState getHomeState() {
    return homeState;
  }

  public void setHomeState(HomeState homeState) {
    this.homeState = homeState;
  }

  public ProfileState getProfileState() {
    return profileState;
  }

  public void setProfileState(ProfileState profileState) {
    this.profileState = profileState;
  }

  public ForgottenState getForgottenState() {
    return forgottenState;
  }

  public void setForgottenState(ForgottenState forgottenState) {
    this.forgottenState = forgottenState;
  }
}
