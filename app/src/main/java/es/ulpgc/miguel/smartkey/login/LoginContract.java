package es.ulpgc.miguel.smartkey.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.FirebaseContract;

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

    void onSuccess();

    void onError();
  }

  interface Model {

    void signIn(String email, String password, FirebaseContract.LoginCallback callback);

  }

  interface Router {
    void navigateToNextScreen();

    void passDataToNextScreen(LoginState state);

    LoginState getDataFromPreviousScreen();

    void navigateToRegisterScreen();

    void navigateToHomeScreen();
  }
}
