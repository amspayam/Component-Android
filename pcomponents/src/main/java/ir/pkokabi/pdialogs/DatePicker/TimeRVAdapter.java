package ir.pkokabi.pdialogs.DatePicker;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.pkokabi.pdialogs.R;


/**
 * Created by P.kokabi on 6/20/2016.
 */

class TimeRVAdapter extends RecyclerView.Adapter<TimeRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> hourList = new ArrayList<>();
    private int selectedItem = -1;

    TimeRVAdapter(ArrayList<String> hourList) {
        this.hourList = hourList;

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView hourTv;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            hourTv = itemView.findViewById(R.id.textView);
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
            holder.hourTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            holder.hourTv.setTextColor(ContextCompat.getColor(context, R.color.blackDialog));
        }
        else {
            holder.hourTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            holder.hourTv.setTextColor(ContextCompat.getColor(context, R.color.grayDialog));
        }
        holder.hourTv.setText(hourList.get(position));
    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    String getTime(int position) {
        return hourList.get(position);
    }

    void updateSelected(int position) {
        this.selectedItem = position;
        notifyDataSetChanged();
    }

}
