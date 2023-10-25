package ucr.ac.cr.boxb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ucr.ac.cr.boxb.databinding.FragmentHomeBinding;
import ucr.ac.cr.boxb.model.Client;

public class HomeFragment extends Fragment {

    // Write a message to the database
    FirebaseFirestore db;

    Client client;
    ClientAdapter clientAdapter;
    ArrayList<Client> listClients= new ArrayList<>();
    private FragmentHomeBinding binding;
    ListView lstV_Home_listClients;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }//End onCreateView

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }//End onDestroyView

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        fillList();
    }//End onCreate

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Initialize components
        lstV_Home_listClients = binding.lstVHomeListClients;


    }//End onViewCreated


    public void fillList(){

        db.collection("Clients")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(getContext(), "Documento: " + document.getData(), Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Toast.makeText(getContext(), "Error obtaining data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        databaseReference.child("Client").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //limpiar la lista
//                listClients.clear();
//
//                client = new Client("DIANA", "21231321");
//                listClients.add(client);
//
//                for(DataSnapshot objSnapshot: snapshot.getChildren()){
//                    client = objSnapshot.getValue(Client.class);
//                    listClients.add(client);
//                    clientAdapter = new ClientAdapter(getContext(), listClients);
//                    lstV_Home_listClients.setAdapter(clientAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }//fin listaDatos
}