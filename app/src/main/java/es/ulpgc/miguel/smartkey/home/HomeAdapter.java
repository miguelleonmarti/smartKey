package es.ulpgc.miguel.smartkey.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.miguel.smartkey.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

  private List<Door> doorList;
  private View.OnClickListener clickListener;

  public HomeAdapter(View.OnClickListener clickListener) {
    this.doorList = new ArrayList<>();
    this.clickListener = clickListener;
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
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.itemView.setTag(doorList.get(position));

    holder.doorNameView.setText(doorList.get(position).getName());
    holder.doorLatitudeView.setText(String.valueOf(doorList.get(position).getLatitude()));
    holder.doorLongitudeView.setText(String.valueOf(doorList.get(position).getLongitude()));

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
