package es.ulpgc.miguel.smartkey.register;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.services.FirebaseContract;


public class RegisterModel implements RegisterContract.Model {

  public static String TAG = RegisterModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth;

  public RegisterModel() {
    firebaseAuth = FirebaseAuth.getInstance();
  }

  @Override
  public void createAccount(final String name, String email, String password, final FirebaseContract.RegisterCallback callback) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              FirebaseUser user = firebaseAuth.getCurrentUser();

              UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                  .setDisplayName(name).build();

              if (user != null) {
                user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                          // user profile updated
                          callback.onRegistered(false);
                        }
                      }
                    });
              }
              // user created
            } else {
              // authentication failed
              callback.onRegistered(true);
            }
          }
        });
  }
}
