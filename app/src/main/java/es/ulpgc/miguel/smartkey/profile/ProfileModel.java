package es.ulpgc.miguel.smartkey.profile;

public class ProfileModel implements ProfileContract.Model {

  public static String TAG = ProfileModel.class.getSimpleName();

  public ProfileModel() {

  }

  @Override
  public String fetchData() {
    // Log.e(TAG, "fetchData()");
    return "Hello";
  }
}
