package es.ulpgc.miguel.smartkey.register;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

public class RegisterPresenter implements RegisterContract.Presenter {

  public static String TAG = RegisterPresenter.class.getSimpleName();

  private WeakReference<RegisterContract.View> view;
  private RegisterViewModel viewModel;
  private RegisterContract.Model model;
  private RegisterContract.Router router;

  public RegisterPresenter(RegisterState state) {
    viewModel = state;
  }

  @Override
  public void injectView(WeakReference<RegisterContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(RegisterContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(RegisterContract.Router router) {
    this.router = router;
  }

  /**
   * Calls the model in order to create a new account.
   * @param name new user's name
   * @param email new user's email
   * @param password new user's password
   */
  @Override
  public void createAccount(String name, String email, String password) {
    model.createAccount(name, email, password, new FirebaseContract.RegisterCallback() {
      @Override
      public void onRegistered(boolean error) {
        if (!error) {
          viewModel.setMessage("User created");
          view.get().displayData(viewModel);
          model.signOut(new FirebaseContract.LogoutCallback() {
            @Override
            public void onLoggedOut() {
              router.navigateToLoginScreen();
            }
          });
        } else {
          // cannot be registered
          viewModel.setMessage("Register failed");
          view.get().displayData(viewModel);
        }
      }
    });
  }

  /**
   * Starts login screen
   */
  @Override
  public void startLoginScreen() {
    router.navigateToLoginScreen();
  }
}
