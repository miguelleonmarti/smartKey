package es.ulpgc.miguel.smartkey.login;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

interface LoginContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(LoginViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void startRegisterScreen();

    void startHomeScreen();

    void signIn(String email, String password);

    void startForgottenScreen();
  }

  interface Model { void signIn(String email, String password, FirebaseContract.LoginCallback callback); }

  interface Router {

    void navigateToRegisterScreen();

    void navigateToHomeScreen();

    void navigateToForgottenScreen();
  }
}
