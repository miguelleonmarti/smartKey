package es.ulpgc.miguel.smartkey.forgotten;

import android.content.Intent;
import android.content.Context;

import es.ulpgc.miguel.smartkey.app.AppMediator;
import es.ulpgc.miguel.smartkey.login.LoginActivity;

public class ForgottenRouter implements ForgottenContract.Router {

  public static String TAG = ForgottenRouter.class.getSimpleName();

  private AppMediator mediator;

  public ForgottenRouter(AppMediator mediator) {
    this.mediator = mediator;
  }

  /**
   * Navigate to login screen.
   */
  @Override
  public void navigateToLoginScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }

}
