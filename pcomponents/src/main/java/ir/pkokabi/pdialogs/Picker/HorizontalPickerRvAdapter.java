package ir.pkokabi.pdialogs.Picker;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.pkokabi.pdialogs.R;


class HorizontalPickerRvAdapter extends RecyclerView.Adapter<HorizontalPickerRvAdapter.ViewHolder> {

    private int start, end, firstItemSize, itemSize, textSize, selectedColor, otherColor;
    private static int VIEW_TYPE_PADDING = 1;
    private static int VIEW_TYPE_ITEM = 2;

    private int selectedItem = -1;

    HorizontalPickerRvAdapter(int start, int end, int firstItemSize
            , int itemSize, int textSize, int selectedColor, int otherColor) {
        this.start = start;
        this.end = end;
        this.firstItemSize = firstItemSize;
        this.itemSize = itemSize;
        this.textSize = textSize;
        this.selectedColor = selectedColor;
        this.otherColor = otherColor;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_horizontal_picker, parent, false);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) v.getLayoutParams();
            layoutParams.width = itemSize;
            layoutParams.height = itemSize;
            v.setLayoutParams(layoutParams);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_horizontal_picker_padding, parent, false);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) v.getLayoutParams();
            layoutParams.width = firstItemSize;
            v.setLayoutParams(layoutParams);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            holder.textView.setText(String.valueOf(position - 1 + start));
            if (position == selectedItem)
                holder.textView.setTextColor(selectedColor);
            else
                holder.textView.setTextColor(otherColor);
        }

    }

    @Override
    public int getItemCount() {
        if (start == 0)
            return (end - start) + 3;
        else
            return ((end - start) + 1) + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == getItemCount() - 1)
            return VIEW_TYPE_PADDING;
        return VIEW_TYPE_ITEM;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    void updateSelected(int position) {
        this.selectedItem = position;
        notifyDataSetChanged();
    }

}
