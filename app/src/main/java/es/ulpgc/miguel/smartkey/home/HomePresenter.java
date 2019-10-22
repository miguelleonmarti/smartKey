package es.ulpgc.miguel.smartkey.home;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.miguel.smartkey.models.Door;
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
        viewModel.setMessage("Logout successful");
        viewModel.setDoorList(new ArrayList<Door>()); // todo: codigo sucio?
        view.get().displayData(viewModel);
        startLoginScreen();
      }
    });
  }

  @Override
  public void fetchDoors() {
    model.fetchDoors(new FirebaseContract.FetchDoors() {
      @Override
      public void onDoorsFetch(ArrayList<Door> doorArrayList) {
        viewModel.setMessage("");
        viewModel.setDoorList(doorArrayList);
        view.get().displayData(viewModel);
      }
    });
  }

  @Override
  public void openDoor(int doorId) {
    model.openDoor(doorId, new FirebaseContract.OpenDoor() {
      @Override
      public void onDoorOpen(boolean error) {
        if (!error) {
          // no hay error todo que hago aqui?
        } else {
          // hay error
        }
      }
    });
  }

  @Override
  public void startLoginScreen() {
    router.navigateToLoginScreen();
  }

  @Override
  public void startMapsScreen() {
    router.navigateToMapsScreen(viewModel.getDoorList());
  }
}
