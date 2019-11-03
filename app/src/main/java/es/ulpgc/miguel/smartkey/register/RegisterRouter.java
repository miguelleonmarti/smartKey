package es.ulpgc.miguel.smartkey.register;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.miguel.smartkey.app.AppMediator;
import es.ulpgc.miguel.smartkey.login.LoginActivity;

public class RegisterRouter implements RegisterContract.Router {

  public static String TAG = RegisterRouter.class.getSimpleName();

  private AppMediator mediator;

  public RegisterRouter(AppMediator mediator) {
    this.mediator = mediator;
  }

  /**
   * Navigates to login screen
   */
  @Override
  public void navigateToLoginScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, LoginActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }
}
