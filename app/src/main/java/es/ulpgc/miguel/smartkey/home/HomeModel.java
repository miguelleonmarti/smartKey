package es.ulpgc.miguel.smartkey.home;

public class HomeModel implements HomeContract.Model {

  public static String TAG = HomeModel.class.getSimpleName();

  public HomeModel() {

  }

  @Override
  public String fetchData() {
    // Log.e(TAG, "fetchData()");
    return "Hello";
  }
}
