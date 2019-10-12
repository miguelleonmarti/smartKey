package es.ulpgc.miguel.smartkey.login;

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

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;
import es.ulpgc.miguel.smartkey.services.Checker;

public class LoginActivity
    extends AppCompatActivity implements LoginContract.View {

  public static String TAG = LoginActivity.class.getSimpleName();

  private LoginContract.Presenter presenter;

  // declaring an instance of FirebaseAuth (to login)
  private FirebaseAuth firebaseAuth;

  // declaring the login button and the edit text
  private Button loginButton, registerButton;
  private EditText emailInput, passwordInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    // hiding the action bar
    getSupportActionBar().hide();

    // getting the FirebaseAuth instance
    firebaseAuth = FirebaseAuth.getInstance();

    // initializing the components of the view
    loginButton = findViewById(R.id.loginButton);
    registerButton = findViewById(R.id.registerButton);
    emailInput = findViewById(R.id.emailInput);
    passwordInput = findViewById(R.id.passwordInput);

    // listener when login button is clicked
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (Checker.validateEmail(emailInput) && Checker.validatePassword(passwordInput)) {
          String email = emailInput.getText().toString();
          String password = passwordInput.getText().toString();
          signIn(email, password);
        }
      }
    });

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.startRegisterScreen();
      }
    });

    // do the setup
    LoginScreen.configure(this);
  }

  /**
   * Firebase method in order to sign in with email and password
   * @param email String
   * @param password String
   * @return Task<AuthResult>
   */
  private Task<AuthResult> signIn(String email, String password) {
    return firebaseAuth.signInWithEmailAndPassword(email, password).
        addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
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

  }
}
