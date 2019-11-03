package es.ulpgc.miguel.smartkey.models;

public class User {
  private String name, email;

  public User() {} // default constructor required by firebase database

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  /*
  getters and setters
   */

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
