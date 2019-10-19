package es.ulpgc.miguel.smartkey.forgotten;

import java.lang.ref.WeakReference;

import es.ulpgc.miguel.smartkey.services.FirebaseContract;

public class ForgottenPresenter implements ForgottenContract.Presenter {

  public static String TAG = ForgottenPresenter.class.getSimpleName();

  private WeakReference<ForgottenContract.View> view;
  private ForgottenViewModel viewModel;
  private ForgottenContract.Model model;
  private ForgottenContract.Router router;

  public ForgottenPresenter(ForgottenState state) {
    viewModel = state;
  }

  @Override
  public void injectView(WeakReference<ForgottenContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(ForgottenContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(ForgottenContract.Router router) {
    this.router = router;
  }

  @Override
  public void sendRecoveryEmail(String email) {
    model.sendRecoveryEmail(email, new FirebaseContract.SendRecoveryEmail() {
      @Override
      public void onEmailSent(boolean error) {
        if (!error) {
          // the email has been sent with no errors
          viewModel.setMessage("Email sent");
        } else {
          // something wrong has happened
          viewModel.setMessage("Fail to send email");
        }
        view.get().displayData(viewModel);
      }
    });
  }
}
