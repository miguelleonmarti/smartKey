package es.ulpgc.miguel.smartkey.register;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import es.ulpgc.miguel.smartkey.R;

public class RegisterActivity
    extends AppCompatActivity implements RegisterContract.View {

  public static String TAG = RegisterActivity.class.getSimpleName();

  private RegisterContract.Presenter presenter;

  // password pattern todo hacer una clase para todos estos metodos y constantes
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

  // declaring the register button and the edit text
  private Button registerButton;
  private EditText nameInput, emailInput, repeatEmailInput, passwordInput;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    // getting the FirebaseAuth instance
    firebaseAuth = FirebaseAuth.getInstance();

    // initializing the components of the view
    registerButton = findViewById(R.id.registerButton);
    nameInput = findViewById(R.id.nameInput);
    emailInput = findViewById(R.id.emailInput);
    repeatEmailInput = findViewById(R.id.repeatEmailInput);
    passwordInput = findViewById(R.id.passwordInput);

    // listener when register button is clicked
    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (validateEmail() && validatePassword() && emailInput.getText().toString().equals(repeatEmailInput.getText().toString()) && !nameInput.getText().toString().isEmpty()) {
          String name = nameInput.getText().toString();
          String email = emailInput.getText().toString();
          String password = passwordInput.getText().toString();
          createAccount(name, email, password);
        }
      }
    });

    // do the setup
    RegisterScreen.configure(this);
  }

  /**
   * todo crear una clase para todos estos métodos
   * @param name
   * @param email
   * @param password
   */
  private void createAccount(String name, String email, String password) {
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
              FirebaseUser user = firebaseAuth.getCurrentUser();
              Toast toast = Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG);
              toast.show();
            } else {
              Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
          }
        });
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

  /**
   * todo hacer una clase para meter todos estos metodos
   * @return
   */
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
  public void injectPresenter(RegisterContract.Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void displayData(RegisterViewModel viewModel) {
    //Log.e(TAG, "displayData()");

    // deal with the data
  }
}
