package es.ulpgc.miguel.smartkey.register;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.FirebaseContract;

interface RegisterContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(RegisterViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void startLoginScreen();

    void createAccount(String name, String email, String password);
  }

  interface Model {
    void createAccount(String name, String email, String password, FirebaseContract.RegisterCallback callback);
  }

  interface Router {
    void navigateToNextScreen();

    void passDataToNextScreen(RegisterState state);

    RegisterState getDataFromPreviousScreen();

    void navigateToLoginScreen();
  }
}
