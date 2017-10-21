package ir.pkokabi.pdialogs.DatePicker;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.pkokabi.pdialogs.R;

/**
 * Created by P.kokabi on 6/20/2016.
 */

class DateRVAdapter extends RecyclerView.Adapter<DateRVAdapter.ViewHolder> {

    private Context context;
    private DialogLinkedMap<String, String> dateList = new DialogLinkedMap<>();
    private int selectedItem = -1;

    DateRVAdapter(DialogLinkedMap<String, String> dateList) {
        this.dateList = dateList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            dateTv = itemView.findViewById(R.id.textView);
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
            holder.dateTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            holder.dateTv.setTextColor(ContextCompat.getColor(context, R.color.blackDialog));
        } else {
            holder.dateTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            holder.dateTv.setTextColor(ContextCompat.getColor(context, R.color.grayDialog));
        }
        holder.dateTv.setText(dateList.getValueByIndex(position));
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    String getDate(int position) {
        return dateList.getKeyByIndex(position);
    }

    String getDateString(int position) {
        return dateList.getValueByIndex(position);
    }

    void updateSelected(int position) {
        this.selectedItem = position;
        notifyDataSetChanged();
    }

}
