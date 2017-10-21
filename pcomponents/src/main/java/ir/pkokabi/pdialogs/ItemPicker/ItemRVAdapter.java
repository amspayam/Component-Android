package ir.pkokabi.pdialogs.ItemPicker;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.pkokabi.pdialogs.DatePicker.DialogLinkedMap;
import ir.pkokabi.pdialogs.R;


/**
 * Created by P.kokabi on 6/20/2016.
 */

class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ViewHolder> {


    private Context context;
    private DialogLinkedMap<String, String> itemList = new DialogLinkedMap<>();
    private int selectedItem = -1;

    ItemRVAdapter(DialogLinkedMap<String, String> itemList) {
        this.itemList = itemList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTv;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemTv = itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == selectedItem) {
            holder.itemTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            holder.itemTv.setTextColor(ContextCompat.getColor(context, R.color.blackDialog));
        } else {
            holder.itemTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            holder.itemTv.setTextColor(ContextCompat.getColor(context, R.color.grayDialog));
        }
        holder.itemTv.setText(itemList.getValueByIndex(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    int getPosition(String id) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.getKeyByIndex(i).equals(id)) {
                return i;
            }
        }
        return 0;
    }

    String getItemKey(int position) {
        return itemList.getKeyByIndex(position);
    }

    String getItemValue(int position) {
        return itemList.getValueByIndex(position);
    }

    void updateSelected(int position) {
        this.selectedItem = position;
        notifyDataSetChanged();
    }

}
