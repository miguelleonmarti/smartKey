package es.ulpgc.miguel.smartkey.login;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

public class LoginPresenter implements LoginContract.Presenter {

  public static String TAG = LoginPresenter.class.getSimpleName();

  private WeakReference<LoginContract.View> view;
  private LoginViewModel viewModel;
  private LoginContract.Model model;
  private LoginContract.Router router;

  public LoginPresenter(LoginState state) {
    viewModel = state;
  }

  @Override
  public void injectView(WeakReference<LoginContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(LoginContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(LoginContract.Router router) {
    this.router = router;
  }

  /**
   * Calls the model in order to sign in the user
   * @param email user's email
   * @param password user's password
   */
  @Override
  public void signIn(String email, String password) {
    model.signIn(email, password, new FirebaseContract.LoginCallback() {
      @Override
      public void onLoggedIn(boolean error) {
        if (!error) {
          // logged in successfully
          startHomeScreen();
        } else {
          // cannot be logged in
          viewModel.setMessage("Authentication failed");
          view.get().displayData(viewModel);
        }
      }
    });
  }

  /**
   * Starts register screen
   */
  @Override
  public void startRegisterScreen() {
    router.navigateToRegisterScreen();
  }

  /**
   * Starts recovery email screen
   */
  @Override
  public void startForgottenScreen() {
    router.navigateToForgottenScreen();
  }

  /**
   * Starts home screen
    */
  @Override
  public void startHomeScreen() {
    router.navigateToHomeScreen();
  }
}
