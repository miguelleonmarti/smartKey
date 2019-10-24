package es.ulpgc.miguel.smartkey.login;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

public class LoginModel implements LoginContract.Model {

  public static String TAG = LoginModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth;

  public LoginModel() {
    this.firebaseAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void signIn(String email, String password, final FirebaseContract.LoginCallback callback) {
    firebaseAuth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              // Sign in success, update UI with the signed-in user's information
              callback.onLoggedIn(false);
            } else {
              // If sign in fails, display a message to the user.
              callback.onLoggedIn(true);
            }
          }
        });
  }
}
