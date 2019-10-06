package es.ulpgc.miguel.smartkey.home;

import java.lang.ref.WeakReference;

interface HomeContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(HomeViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void fetchData();
  }

  interface Model {
    String fetchData();
  }

  interface Router {
    void navigateToNextScreen();

    void passDataToNextScreen(HomeState state);

    HomeState getDataFromPreviousScreen();
  }
}
