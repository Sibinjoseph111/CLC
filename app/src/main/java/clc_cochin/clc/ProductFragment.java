package clc_cochin.clc;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    private static final String TAG = "ProductFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView productRecyclerView;
    private productRecyclerViewAdapter madapter;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_product, container, false);
        firestoreDB = FirebaseFirestore.getInstance();
        productRecyclerView = (RecyclerView) view.findViewById(R.id.events_lst);


        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        productRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productRecyclerView.getContext(),
                                                                    recyclerLayoutManager.getOrientation());
        productRecyclerView.addItemDecoration(dividerItemDecoration);

        getDocumentsFromCollection();



        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void getDocumentsFromCollection() {
        firestoreDB.collection("products").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //List<Event> eventList = new ArrayList<>();
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();

                            productRecyclerViewAdapter recyclerViewAdapter = new productRecyclerViewAdapter(documents, getActivity(), firestoreDB);

                            productRecyclerView.setAdapter(recyclerViewAdapter);

                            Toast.makeText(getContext(), "success " + task.getResult().size(), Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        firestoreDB.collection("products")
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                    }
                });
    }
}



