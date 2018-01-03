package clc_cochin.clc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import clc_cochin.clc.R;

/**
 * Created by HP on 03-01-2018.
 */

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private List<DocumentSnapshot> documents;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public ProductAdapter(List<DocumentSnapshot> list, Context ctx, FirebaseFirestore firestore) {
        documents = list;
        context = ctx;
        firestoreDB = firestore;
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    @Override
    public ProductAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        ProductAdapter.ViewHolder viewHolder =
                new ProductAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
        final int itemPos = position;
        final DocumentSnapshot snapshot = documents.get(position);
        holder.item_name.setText(snapshot.getString("Product"));
        holder.price.setText("Rs " + snapshot.getString("Cost") + "/" + snapshot.getString("Per"));


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
