package es.ulpgc.miguel.smartkey.home;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.miguel.smartkey.services.FirebaseContract;

public class HomePresenter implements HomeContract.Presenter {

  public static String TAG = HomePresenter.class.getSimpleName();

  private WeakReference<HomeContract.View> view;
  private HomeViewModel viewModel;
  private HomeContract.Model model;
  private HomeContract.Router router;

  public HomePresenter(HomeState state) {
    viewModel = state;
  }

  @Override
  public void injectView(WeakReference<HomeContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(HomeContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(HomeContract.Router router) {
    this.router = router;
  }

  @Override
  public void signOut() {
    model.signOut(new FirebaseContract.LogoutCallback() {
      @Override
      public void onLoggedOut() {
        startLoginScreen();
        viewModel.message = "Logout successful";
        view.get().displayData(viewModel);
      }
    });
  }

  @Override
  public void fetchDoors() {
    model.fetchDoors(new FirebaseContract.FetchDoors() {
      @Override
      public void onDoorsFetch(ArrayList<Door> doorArrayList) {
        viewModel.doorList = doorArrayList;
        view.get().displayData(viewModel);
      }
    });
  }

  @Override
  public void startLoginScreen() {
    router.navigateToLoginScreen();
  }
}
