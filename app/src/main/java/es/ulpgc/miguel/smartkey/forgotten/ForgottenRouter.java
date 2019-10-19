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

  @Override
  public void navigateToNextScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, ForgottenActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void navigateToLoginScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void passDataToNextScreen(ForgottenState state) {
    mediator.setForgottenState(state);
  }

  @Override
  public ForgottenState getDataFromPreviousScreen() {
    ForgottenState state = mediator.getForgottenState();
    return state;
  }
}
