package es.ulpgc.miguel.smartkey.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.R;

public class LoginActivity
    extends AppCompatActivity implements LoginContract.View {

  public static String TAG = LoginActivity.class.getSimpleName();

  private LoginContract.Presenter presenter;

  // password pattern
  private static final Pattern PASSWORD_PATTERN =
      Pattern.compile("^" +
                      "(?=.*[0-9])" +         // at least 1 digit
                      "(?=.*[a-z])" +         // at least 1 lower case letter
                      "(?=.*[A-Z])" +         // at least 1 upper case letter
                      "(?=.*[@#$%^&+=])" +    // at least 1 special character
                      "(?=\\S+$)" +           // no white spaces
                      ".{8,}" +               // at least 8 characters
                      "$");

  // declaring an instance of FirebaseAuth (to login)
  private FirebaseAuth firebaseAuth;

  // declaring the login button
  private Button loginButton;
  private EditText emailInput, passwordInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    // initializing the FirebaseAuth instance
    firebaseAuth = FirebaseAuth.getInstance();

    // initializing the components of the view
    loginButton = findViewById(R.id.loginButton);
    emailInput = findViewById(R.id.emailInput);
    passwordInput = findViewById(R.id.passwordInput);

    // listener when login button is clicked
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (validateEmail() && validatePassword()) {
          String email = emailInput.getText().toString();
          String password = passwordInput.getText().toString();
          Log.d("email", email);
          Log.d("password", password);
          firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Toast toast = Toast.makeText(getApplicationContext(), "User logged in: " + user.getEmail(), Toast.LENGTH_LONG);
                toast.show();
              } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
              }
            }
          });
        }
      }
    });

    // do the setup
    LoginScreen.configure(this);
  }

  /**
   * //todo este método se puede quitar cuando se pase a otra pantalla
   */
  @Override
  protected void onStart() {
    super.onStart();

    // check if user is signed in (not-null) and update UI accordingly
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    if (currentUser != null) {
      Toast toast = Toast.makeText(getApplicationContext(), "User logged in: " + currentUser.getEmail(), Toast.LENGTH_LONG);
      toast.show();
    } else {
      Toast toast = Toast.makeText(getApplicationContext(), "User not logged in", Toast.LENGTH_LONG);
      toast.show();
    }

  }

  private boolean validateEmail() {
    String email = emailInput.getText().toString().trim();

    if (email.isEmpty()) {
      emailInput.setError("Field cannot be empty");
      return false;
    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      emailInput.setError("Please enter a valid email address");
      return false;
    } else {
      emailInput.setError(null);
      return true;
    }
  }

  private boolean validatePassword() {
    String password = passwordInput.getText().toString().trim();

    if (password.isEmpty()) {
      passwordInput.setError("Field cannot be empty");
      return false;
    } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
      passwordInput.setError("Password too week");
      return false;
    } else {
      passwordInput.setError(null);
      return true;
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    // do some work
    presenter.fetchData();
  }

  @Override
  public void injectPresenter(LoginContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(LoginViewModel viewModel) {
    //Log.e(TAG, "displayData()");

    // deal with the data


  }
}
