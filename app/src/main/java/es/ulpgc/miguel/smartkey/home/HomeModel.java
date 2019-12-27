package es.ulpgc.miguel.smartkey.home;

import android.location.Location;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.models.Door;
import es.ulpgc.miguel.smartkey.services.firebase.FirebaseContract;

public class HomeModel implements HomeContract.Model {

  public static String TAG = HomeModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth; // in order to sign out the user
  private DatabaseReference databaseReference; // database's reference

  public HomeModel() {
    this.firebaseAuth = FirebaseAuth.getInstance();
    this.databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smartkey-2fae0.firebaseio.com/"); // todo: revisar si debe ser así
  }

  /**
   * Close the user's current session
   * @param callback When process has finished
   */
  @Override
  public void signOut(FirebaseContract.LogoutCallback callback) {
    firebaseAuth.signOut();
    callback.onLoggedOut();
  }

  /**
   * Look for the doors in which the user has permission to access and that are also within a
   * certain radius
   *
   * @param location The user's current location
   * @param callback When process has finished
   */
  @Override
  public void fetchDoors(final Location location, final FirebaseContract.FetchDoors callback) {
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> doors = dataSnapshot.child("doors").getChildren();
        ArrayList<Door> doorList = new ArrayList<>();
        for (DataSnapshot door : doors) {
          Door item = door.getValue(Door.class);
          // if the current user's uid is on the list of users with permission
          // of the door, it will add this door to the local list (home activity)
          Location doorLocation = new Location("");
          doorLocation.setLatitude(Float.parseFloat(item.getLatitude()));
          doorLocation.setLongitude(Float.parseFloat(item.getLongitude()));

          float distanceInMeters = location.distanceTo(doorLocation);

          // in addition, only if the user is within a certain distance will the door be added to the list
          if (item.getUsers().contains(firebaseAuth.getCurrentUser().getUid()) && distanceInMeters < 100000) {
            doorList.add(item);
          }
        }

        callback.onDoorsFetch(doorList);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d(TAG, String.valueOf(databaseError));
      }
    });
  }

}
