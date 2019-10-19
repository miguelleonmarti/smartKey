package es.ulpgc.miguel.smartkey.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import es.ulpgc.miguel.smartkey.map.MapsActivity;
import es.ulpgc.miguel.smartkey.app.AppMediator;
import es.ulpgc.miguel.smartkey.login.LoginActivity;
import es.ulpgc.miguel.smartkey.models.Door;

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

  @Override
  public void navigateToMapsScreen(ArrayList<Door> doorList) { // todo importacion ?
    Context context = mediator.getApplicationContext();

    Bundle bundle = new Bundle();
    bundle.putSerializable("doorList", doorList);

    Intent intent = new Intent(context, MapsActivity.class);
    intent.putExtras(bundle);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }
}
