package es.ulpgc.miguel.smartkey.home;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import es.ulpgc.miguel.smartkey.services.FirebaseContract;

public class HomeModel implements HomeContract.Model {

  public static String TAG = HomeModel.class.getSimpleName();

  private FirebaseAuth firebaseAuth;
  private DatabaseReference databaseReference;

  public HomeModel() {
    this.firebaseAuth = FirebaseAuth.getInstance();
    this.databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smartkey-2fae0.firebaseio.com/"); // todo: revisar si debe ser así
  }

  @Override
  public void signOut(FirebaseContract.LogoutCallback callback) {
    firebaseAuth.signOut();
    callback.onLoggedOut();
  }

  @Override
  public void fetchDoors(final FirebaseContract.FetchDoors callback) {
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> doors = dataSnapshot.child("doors").getChildren();
        ArrayList<Door> doorList = new ArrayList<>();
        for (DataSnapshot door: doors) {
          Door item = door.getValue(Door.class);
          doorList.add(item);
        }

        callback.onDoorsFetch(doorList);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
