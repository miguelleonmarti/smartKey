package es.ulpgc.miguel.smartkey.forgotten;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.services.FirebaseContract;

public class ForgottenModel implements ForgottenContract.Model {

  public static String TAG = ForgottenModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth;

  public ForgottenModel() {
    this.firebaseAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void sendRecoveryEmail(String email, final FirebaseContract.SendRecoveryEmail callback) {
    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          // successful operation
          callback.onEmailSent(false);
        } else {
          callback.onEmailSent(true);
        }
      }
    });
  }
}
