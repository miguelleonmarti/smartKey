package es.ulpgc.miguel.smartkey.forgotten;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

interface ForgottenContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayData(ForgottenViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);

    void injectModel(Model model);

    void injectRouter(Router router);

    void sendRecoveryEmail(String email);

    void startLoginScreen();
  }

  interface Model { void sendRecoveryEmail(String email, FirebaseContract.SendRecoveryEmail callback); }

  interface Router { void navigateToLoginScreen(); }
}
