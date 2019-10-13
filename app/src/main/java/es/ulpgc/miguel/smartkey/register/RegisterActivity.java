package es.ulpgc.miguel.smartkey.register;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;
import es.ulpgc.miguel.smartkey.services.Checker;

public class RegisterActivity
    extends AppCompatActivity implements RegisterContract.View {

  public static String TAG = RegisterActivity.class.getSimpleName();

  private RegisterContract.Presenter presenter;

  // declaring an instance of FirebaseAuth (to register)
  private FirebaseAuth firebaseAuth;

  // declaring the register button and the edit text
  private Button registerButton, loginButton;
  private EditText emailInput, repeatEmailInput, passwordInput, nameInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    // hiding the action bar
    getSupportActionBar().hide();

    // getting the FirebaseAuth instance
    firebaseAuth = FirebaseAuth.getInstance();

    // initializing the components of the view
    registerButton = findViewById(R.id.registerButton);
    loginButton = findViewById(R.id.loginButton);
    nameInput = findViewById(R.id.nameInput);
    emailInput = findViewById(R.id.emailInput);
    repeatEmailInput = findViewById(R.id.repeatEmailInput);
    passwordInput = findViewById(R.id.passwordInput);

    // listener when register button is clicked
    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (Checker.validateEmail(emailInput) && Checker.validateEmails(emailInput, repeatEmailInput)
            && Checker.validatePassword(passwordInput)) {
          String name = nameInput.getText().toString();
          String email = emailInput.getText().toString();
          String password = passwordInput.getText().toString();
          createAccount(name, email, password);
        }
      }
    });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.startLoginScreen();
      }
    });

    // do the setup
    RegisterScreen.configure(this);
  }

  /**
   *  todo corregir
   * @param name
   * @param email
   * @param password
   */
  private void createAccount(final String name, String email, String password) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
                          Log.d(TAG, "User profile updated.");
                        }
                      }
                    });
              }

              Toast toast = Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG);
              toast.show();
            } else {
              Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  @Override
  protected void onResume() {
    super.onResume();

    // do some work
    presenter.fetchData();
  }

  @Override
  public void injectPresenter(RegisterContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(RegisterViewModel viewModel) {

  }
}
