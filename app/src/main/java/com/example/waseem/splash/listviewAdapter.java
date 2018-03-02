package com.example.waseem.splash;
/**
 * Created by Waseem on 2/8/2018.
 */

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.Gravity.CENTER;

public class listviewAdapter extends RecyclerView.Adapter<listviewAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private Context myView;
    private MyViewHolder holder;
    private View inflatedView;

    public List<Map.Entry<String, String>> mData = Collections.emptyList();
    private View toastView;


    public listviewAdapter(HashMap<String, String> athkarRep, Context activity) {
        inflater = LayoutInflater.from(activity);

        mData = new ArrayList<>();

        mData.addAll(athkarRep.entrySet());
        myView = activity;

    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflatedView = inflater.inflate(R.layout.listviewlayout, parent, false);
        holder = new MyViewHolder(inflatedView);
        return holder;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position >= 0) {
            Map.Entry<String, String> current = mData.get(position);
            holder.getTextThikr().setText(mData.get(position).getKey());
            holder.getRepThikr().setText(mData.get(position).getValue());


        }
    }


    /**
     * Called by RecyclerView when it starts observing this Adapter.
     * <p>
     * Keep in mind that same adapter may be observed by multiple RecyclerViews.
     *
     * @param recyclerView The RecyclerView instance which started observing this adapter.
     * @see #onDetachedFromRecyclerView(RecyclerView)
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView textThikr;
        private TextView repThikr;
        private TextView toastText;


        public MyViewHolder(View v) {
            super(v);
            textThikr = ((TextView) v.findViewById(R.id.thikrText));
            repThikr = ((TextView) v.findViewById(R.id.repetations));
            toastView = inflater.inflate(R.layout.toast_layout, (ViewGroup) v.getParent(), false);
            toastText = ((TextView) toastView.findViewById(R.id.ToastText));
            repThikr.setOnClickListener(this);

        }


        public TextView getTextThikr() {
            return textThikr;
        }

        public TextView getRepThikr() {
            return repThikr;
        }


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (v.getId() == repThikr.getId()) {
                int counter = Integer.parseInt(repThikr.getText().toString());
                //  counter = Integer.parseInt(mData.get(holder.getAdapterPosition()).getValue());
                if (counter > 1) {
                    Toast toast = Toast.makeText(myView, "" + counter, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    counter--;
                    mData.get(getAdapterPosition()).setValue(Integer.toString(counter));
                    notifyItemChanged(getAdapterPosition());


                } else {
                    Toast toast = Toast.makeText(myView, "" + counter, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    if (holder.getAdapterPosition() >= 0) {
                        mData.remove(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());

                    }

                }
            }


        }


    }


}








