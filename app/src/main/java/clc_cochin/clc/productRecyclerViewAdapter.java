package clc_cochin.clc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.games.event.Event;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by Shyam PC on 29-12-2017.
 */



public class productRecyclerViewAdapter extends
        RecyclerView.Adapter<productRecyclerViewAdapter.ViewHolder> {

    private List<Event> eventsList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public productRecyclerViewAdapter(List<Event> list, Context ctx, FirebaseFirestore firestore) {
        eventsList = list;
        context = ctx;
        firestoreDB = firestore;
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    @Override
    public productRecyclerViewAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        productRecyclerViewAdapter.ViewHolder viewHolder =
                new productRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(productRecyclerViewAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final Event event = eventsList.get(position);
        holder.item_name.setText(event.getName());
        holder.price.setText((int) event.getValue());


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name;
        public TextView price;


        public ViewHolder(View view) {
            super(view);

            item_name = view.findViewById(R.id.List_maintext);
            price = view.findViewById(R.id.List_subtext);

        }
    }
}