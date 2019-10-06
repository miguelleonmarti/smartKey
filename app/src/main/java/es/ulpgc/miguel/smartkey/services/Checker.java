package es.ulpgc.miguel.smartkey.services;

import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;

public class Checker {

  private static final String AT_LEAST_ONE_DIGIT = "(?=.*[0-9])";
  private static final String AT_LEAST_ONE_LOWER_CASE_LETTER = "(?=.*[a-z])";
  private static final String AT_LEAST_ONE_UPPER_CASE_LETTER = "(?=.*[A-Z])";
  private static final String AT_LEAST_ONE_SPECIAL_CHARACTER = "(?=.*[@#$%^&+=])";
  private static final String NO_WHITE_SPACES = "(?=\\S+$)";
  private static final String AT_LEAST_EIGHT_CHARACTERS = ".{8,}";

  private static final Pattern PASSWORD_PATTERN =
      Pattern.compile("^" +
          AT_LEAST_ONE_DIGIT +
          AT_LEAST_ONE_LOWER_CASE_LETTER +
          AT_LEAST_ONE_UPPER_CASE_LETTER +
          AT_LEAST_ONE_SPECIAL_CHARACTER +
          NO_WHITE_SPACES +
          AT_LEAST_EIGHT_CHARACTERS +
          "$");

  /**
   * checks whether the email follows a pattern or not
   * @param emailInput where the user writes down its email
   * @return true if there are no errors
   */
  public static boolean validateEmail(@NonNull EditText emailInput) {
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

  public static boolean validatePassword(@NonNull EditText passwordInput) {
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

  public static boolean validateEmails(@NonNull EditText firstEmailInput, @NonNull EditText secondEmailInput) {
    return firstEmailInput.getText().toString().equals(secondEmailInput.getText().toString());
  }
}
