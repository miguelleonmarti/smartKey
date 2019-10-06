package es.ulpgc.miguel.smartkey.login;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;

import es.ulpgc.miguel.smartkey.app.AppMediator;

public class LoginScreen {

  public static void configure(LoginContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = (AppMediator) context.get().getApplication();
    LoginState state = mediator.getLoginState();

    LoginContract.Router router = new LoginRouter(mediator);
    LoginContract.Presenter presenter = new LoginPresenter(state);
    LoginContract.Model model = new LoginModel();
    presenter.injectModel(model);
    presenter.injectRouter(router);
    presenter.injectView(new WeakReference<>(view));

    view.injectPresenter(presenter);

  }
}
