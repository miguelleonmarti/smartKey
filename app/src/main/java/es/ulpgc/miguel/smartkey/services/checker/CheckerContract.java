package es.ulpgc.miguel.smartkey.services.checker;

import android.widget.EditText;

import androidx.annotation.NonNull;

public interface CheckerContract {

  /**
   * Validates or checks if it is a valid email format by pattern
   * @param emailInput email written by a user
   * @return true if correct, false if it is not valid
   */
  boolean validateEmail(@NonNull EditText emailInput);

  /**
   * Validates or checks if it is a valid password format by pattern
   * @param passwordInput password written by a user
   * @return true if strong enough, false if it is weak
   */
  boolean validatePassword(@NonNull EditText passwordInput);

  /**
   * Checks if both emails are equal
   * @param firstEmailInput first email
   * @param secondEmailInput second email
   * @return true if equal
   */
  boolean validateEmails(@NonNull EditText firstEmailInput, @NonNull EditText secondEmailInput);
}
