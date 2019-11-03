package es.ulpgc.miguel.smartkey.home;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.miguel.smartkey.models.Door;

public class HomeViewModel {

  private String message; // represents the logout message
  private ArrayList<Door> doorList = new ArrayList<>(); // list of fetched doors

  /*
  getters and setter of message and doors list
   */
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ArrayList<Door> getDoorList() {
    return doorList;
  }

  public void setDoorList(ArrayList<Door> doorList) {
    this.doorList = doorList;
  }
}
