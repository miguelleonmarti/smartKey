package es.ulpgc.miguel.smartkey.home;

import android.content.Context;
import android.content.Intent;

import es.ulpgc.miguel.smartkey.app.AppMediator;
import es.ulpgc.miguel.smartkey.login.LoginActivity;

public class HomeRouter implements HomeContract.Router {

  public static String TAG = HomeRouter.class.getSimpleName();

  private AppMediator mediator;

  public HomeRouter(AppMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void navigateToNextScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, HomeActivity.class);
    context.startActivity(intent);
  }

  @Override
  public void passDataToNextScreen(HomeState state) {
    mediator.setHomeState(state);
  }

  @Override
  public HomeState getDataFromPreviousScreen() {
    HomeState state = mediator.getHomeState();
    return state;
  }

  @Override
  public void navigateToLoginScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }
}
