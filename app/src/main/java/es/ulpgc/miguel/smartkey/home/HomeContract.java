package es.ulpgc.miguel.smartkey.home;

import android.location.Location;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.miguel.smartkey.models.Door;
import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

interface HomeContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(HomeViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void signOut();

    void startLoginScreen();

    void fetchDoors(Location location);

    void startMapsScreen();
  }

  interface Model {
    void signOut(FirebaseContract.LogoutCallback callback);

    void fetchDoors(Location location, FirebaseContract.FetchDoors callback);
  }

  interface Router {
    void navigateToLoginScreen();

    void navigateToMapsScreen(ArrayList<Door> doorList);
  }

}
