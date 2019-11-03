package es.ulpgc.miguel.smartkey.register;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.models.User;
import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;


public class RegisterModel implements RegisterContract.Model {

  public static String TAG = RegisterModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth;
  private DatabaseReference databaseReference;

  public RegisterModel() {
    firebaseAuth = FirebaseAuth.getInstance();
    databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  /**
   * Sign up a user on the app by email and password
   * @param name new user's name
   * @param email new user's email
   * @param password new user's password
   * @param callback notifies the presenter on complete
   */
  @Override
  public void createAccount(final String name, final String email, String password, final FirebaseContract.RegisterCallback callback) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              final FirebaseUser user = firebaseAuth.getCurrentUser();

              UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                  .setDisplayName(name).build(); // it sets the user's display name by its name

              if (user != null) {
                user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                          // user profile updated
                          writeNewUser(user.getUid(), name, email); // saving user on database
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

  /**
   * Saving user in the Firebase Database
   * @param userId user's id
   * @param name user's name
   * @param email user's email
   */
  private void writeNewUser(String userId, String name, String email) {
    User user = new User(name, email);
    databaseReference.child("users").child(userId).setValue(user);
  }

  /**
   * Sign out the current user
   * @param callback notifies the presenter on complete
   */
  @Override
  public void signOut(FirebaseContract.LogoutCallback callback) {
    firebaseAuth.signOut();
    callback.onLoggedOut();
  }
}
