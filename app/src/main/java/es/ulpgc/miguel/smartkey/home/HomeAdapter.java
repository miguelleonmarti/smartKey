package es.ulpgc.miguel.smartkey.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.miguel.smartkey.R;
import es.ulpgc.miguel.smartkey.models.Door;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

  private List<Door> doorList;
  private RecyclerViewOnClick listener;

  public HomeAdapter(RecyclerViewOnClick listener) {
    this.doorList = new ArrayList<>();
    this.listener = listener;
  }

  public void addItem(Door door) {
    doorList.add(door);
    notifyDataSetChanged();
  }

  public void addItems(List<Door> supportItemList) {
    this.doorList.addAll(supportItemList);
    notifyDataSetChanged();
  }

  public void setItems(List<Door> shopItemList) {
    this.doorList = shopItemList;
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
    View view = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.door_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, final int position) {
    holder.itemView.setTag(doorList.get(position));

    holder.doorNameView.setText(doorList.get(position).getName());
    holder.doorLatitudeView.setText(String.valueOf(doorList.get(position).getLatitude()));
    holder.doorLongitudeView.setText(String.valueOf(doorList.get(position).getLongitude()));

    if (doorList.get(position).isOpen()) {
      holder.openButtonView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_unlock);
      holder.openButtonView.setBackgroundResource(R.drawable.btn_open_rounded);
      holder.openButtonView.setText("Close");
    } else {
      holder.openButtonView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_lock);
      holder.openButtonView.setBackgroundResource(R.drawable.btn_closed_rounded);
      holder.openButtonView.setText("Open");
    }

    holder.openButtonView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String address = doorList.get(position).getAddress();
        listener.onClick(address);
      }
    });
  }

  @Override
  public int getItemCount() {
    return doorList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    final TextView doorNameView, doorLatitudeView, doorLongitudeView;
    final Button openButtonView;

    public ViewHolder(View view) {
      super(view);
      doorNameView = view.findViewById(R.id.doorName);
      doorLatitudeView = view.findViewById(R.id.doorLatitude);
      doorLongitudeView = view.findViewById(R.id.doorLongitude);
      openButtonView = view.findViewById(R.id.openButton);
    }
  }


}
