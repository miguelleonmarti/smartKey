package es.ulpgc.miguel.smartkey.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;
import es.ulpgc.miguel.smartkey.services.checker.Checker;

public class RegisterActivity
    extends AppCompatActivity implements RegisterContract.View {

  public static String TAG = RegisterActivity.class.getSimpleName();

  private RegisterContract.Presenter presenter;

  // declaring an instance of FirebaseAuth (to register)
  private FirebaseAuth firebaseAuth;

  // declaring the register button and the edit text
  private Button registerButton, loginButton;
  private EditText emailInput, repeatEmailInput, passwordInput, nameInput;

  // declaring inputs checker
  private Checker checker;

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

    // initializing the checker
    checker = new Checker();

    // listener when register button is clicked
    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (checker.validateEmail(emailInput) && checker.validateEmails(emailInput, repeatEmailInput)
            && checker.validatePassword(passwordInput)) {
          String name = nameInput.getText().toString();
          String email = emailInput.getText().toString();
          String password = passwordInput.getText().toString();
          presenter.createAccount(name, email, password);
        }
      }
    });

    // listener when login button is clicked
    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.startLoginScreen();
      }
    });

    // do the setup
    RegisterScreen.configure(this);
  }

  @Override
  public void injectPresenter(RegisterContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(RegisterViewModel viewModel) {
    Toast.makeText(RegisterActivity.this, viewModel.message, Toast.LENGTH_SHORT).show();
  }
}
