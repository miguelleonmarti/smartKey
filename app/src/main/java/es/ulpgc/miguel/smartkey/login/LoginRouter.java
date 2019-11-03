package es.ulpgc.miguel.smartkey.login;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.miguel.smartkey.app.AppMediator;
import es.ulpgc.miguel.smartkey.forgotten.ForgottenActivity;
import es.ulpgc.miguel.smartkey.home.HomeActivity;
import es.ulpgc.miguel.smartkey.register.RegisterActivity;

public class LoginRouter implements LoginContract.Router {

  public static String TAG = LoginRouter.class.getSimpleName();

  private AppMediator mediator;

  public LoginRouter(AppMediator mediator) {
    this.mediator = mediator;
  }

  /**
   * Navigates to register screen
   */
  @Override
  public void navigateToRegisterScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, RegisterActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  /**
   * Navigates to send recovery email screen
   */
  @Override
  public void navigateToForgottenScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, ForgottenActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  /**
   * Navigates to home screen
   */
  @Override
  public void navigateToHomeScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, HomeActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }
}
