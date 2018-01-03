package clc_cochin.clc;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import clc_cochin.clc.Adapter.ServiceAdapter;


public class ServiceFragment extends Fragment {

    private static final String TAG = "ServiceFragment";
    private FirebaseFirestore firestoreDB;
    private RecyclerView serviceRecyclerView;


    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_service, container, false);

        firestoreDB = FirebaseFirestore.getInstance();
        serviceRecyclerView = view.findViewById(R.id.Service_RecyclerView);


        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        serviceRecyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(serviceRecyclerView.getContext(),
                recyclerLayoutManager.getOrientation());
        serviceRecyclerView.addItemDecoration(dividerItemDecoration);

        getDocumentsFromCollection();

        return view;
    }

    public void getDocumentsFromCollection() {
        firestoreDB.collection("services").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //List<Event> eventList = new ArrayList<>();
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();

                            ServiceAdapter recyclerViewAdapter = new ServiceAdapter(documents, getActivity(), firestoreDB);

                            serviceRecyclerView.setAdapter(recyclerViewAdapter);


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
