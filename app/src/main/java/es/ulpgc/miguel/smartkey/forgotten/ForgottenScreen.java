package es.ulpgc.miguel.smartkey.forgotten;

import java.lang.ref.WeakReference;

import androidx.fragment.app.FragmentActivity;
import es.ulpgc.miguel.smartkey.app.AppMediator;

public class ForgottenScreen {

  public static void configure(ForgottenContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = (AppMediator) context.get().getApplication();
    ForgottenState state = mediator.getForgottenState();

    ForgottenContract.Router router = new ForgottenRouter(mediator);
    ForgottenContract.Presenter presenter = new ForgottenPresenter(state);
    ForgottenContract.Model model = new ForgottenModel();
    presenter.injectModel(model);
    presenter.injectRouter(router);
    presenter.injectView(new WeakReference<>(view));

    view.injectPresenter(presenter);

  }
}
