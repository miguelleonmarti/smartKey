package es.ulpgc.miguel.smartkey.forgotten;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

public class ForgottenModel implements ForgottenContract.Model {

  public static String TAG = ForgottenModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth;

  public ForgottenModel() {
    this.firebaseAuth = FirebaseAuth.getInstance();
  }

  /**
   * Calls Firebase's sendRecoveryEmail function. On Complete, it will notify the presenter with a
   * callback.
   * @param email the user's email
   * @param callback notifies the presenter if there has been an error on complete
   */
  @Override
  public void sendRecoveryEmail(String email, final FirebaseContract.SendRecoveryEmail callback) {
    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          // successful operation
          callback.onEmailSent(false);
        } else {
          // something wrong happened
          callback.onEmailSent(true);
        }
      }
    });
  }
}
