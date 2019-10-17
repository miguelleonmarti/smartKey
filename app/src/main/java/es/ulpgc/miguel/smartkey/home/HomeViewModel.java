package es.ulpgc.miguel.smartkey.home;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel {

  private String message;
  private List<Door> doorList = new ArrayList<>();

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<Door> getDoorList() {
    return doorList;
  }

  public void setDoorList(List<Door> doorList) {
    this.doorList = doorList;
  }
}
