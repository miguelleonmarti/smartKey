package es.ulpgc.miguel.smartkey.login;

import java.lang.ref.WeakReference;

interface LoginContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(LoginViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void fetchData();

    void startRegisterScreen();
  }

  interface Model {
    String fetchData();
  }

  interface Router {
    void navigateToNextScreen();

    void passDataToNextScreen(LoginState state);

    LoginState getDataFromPreviousScreen();

    void navigateToRegisterScreen();
  }
}
