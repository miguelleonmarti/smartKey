package es.ulpgc.miguel.smartkey.home;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.FirebaseContract;

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

    void fetchDoors();
  }

  interface Model {
    void signOut(FirebaseContract.LogoutCallback callback);

    void fetchDoors(FirebaseContract.FetchDoors callback);
  }

  interface Router {
    void navigateToNextScreen();

    void passDataToNextScreen(HomeState state);

    HomeState getDataFromPreviousScreen();

    void navigateToLoginScreen();
  }
}
