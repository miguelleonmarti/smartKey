package es.ulpgc.miguel.smartkey.services;

import android.widget.EditText;

import androidx.annotation.NonNull;

public interface CheckerContract {

  boolean validateEmail(@NonNull EditText emailInput);

  boolean validatePassword(@NonNull EditText passwordInput);

  boolean validateEmails(@NonNull EditText firstEmailInput, @NonNull EditText secondEmailInput);
}
