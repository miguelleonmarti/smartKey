package es.ulpgc.miguel.smartkey.register;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

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

    void signOut(FirebaseContract.LogoutCallback callback);
  }

  interface Router { void navigateToLoginScreen(); }
}
