package es.ulpgc.miguel.smartkey.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import es.ulpgc.miguel.smartkey.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

  private List<Door> doorList;

  public HomeAdapter() {
    this.doorList = new ArrayList<>();
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
  }

  @Override
  public int getItemCount() {
    return doorList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    final TextView doorNameView;

    public ViewHolder(View view) {
      super(view);
      doorNameView = view.findViewById(R.id.doorName);
    }
  }
}
