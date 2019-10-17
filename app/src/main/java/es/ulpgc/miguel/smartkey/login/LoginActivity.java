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

  // declaring inputs checker
  private Checker checker;

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

    // initializing the checker
    checker = new Checker();

    // listener when login button is clicked
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (checker.validateEmail(emailInput) && checker.validatePassword(passwordInput)) {
          String email = emailInput.getText().toString();
          String password = passwordInput.getText().toString();
          presenter.signIn(email, password);
        }
      }
    });

    // listener when register button is clicked
    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        presenter.startRegisterScreen();
      }
    });

    // do the setup
    LoginScreen.configure(this);
  }

  @Override
  protected void onResume() {
    super.onResume();

  }

  @Override
  public void injectPresenter(LoginContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(LoginViewModel viewModel) {
    Toast.makeText(getApplicationContext(), viewModel.message, Toast.LENGTH_LONG).show();
  }
}
