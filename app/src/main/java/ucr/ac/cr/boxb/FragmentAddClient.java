package ucr.ac.cr.boxb;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ucr.ac.cr.boxb.databinding.FragmentAddClientBinding;
import ucr.ac.cr.boxb.databinding.FragmentLoginBinding;

public class FragmentAddClient extends Fragment {


    private FragmentAddClientBinding binding;

    private FirebaseAuth mAuth;
    // Initialize Firebase Auth
    private EditText txtName, txtLastName, txtEmail, txtID;

    private String uid;

    private Button btn_addClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddClientBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        txtEmail = binding.txtEmailAddClient;
        txtName = binding.txtNameAddClient;
        txtLastName = binding.txtLastNameAddClient;
        txtID = binding.txtAddClientCedula;


        btn_addClient = binding.btnClientAdd;

        btn_addClient.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if (txtName.getText().toString().isEmpty()){
                    txtName.setError("Fill all the blanks");
                } else if (txtID.getText().toString().isEmpty()) {
                    txtID.setError("Fill all the blanks");
                } else if (txtLastName.getText().toString().isEmpty()) {
                    txtLastName.setError("Fill all the blanks");
                } else if (txtEmail.getText().toString().isEmpty()) {
                    txtEmail.setError("Fill all the blanks");
                }else{

                    // Create a new user with a first and last name
                    Map<String, Object> user = new HashMap<>();
                    user.put("documentID", txtID.getText().toString());
                    user.put("name", txtName.getText().toString());
                    user.put("lastName", txtLastName.getText().toString());
                    user.put("email", txtEmail.getText().toString());
                    user.put("uid_user", uid);

// Add a new document with a generated ID
                    db.collection("Clients")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    limpiar();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }

            }
        });

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            System.out.println("El usuario esta login: " + currentUser.getUid());
            uid = currentUser.getUid();
        }else{
            System.out.println("El usuario no esta login");

        }
    }

    public void limpiar(){
        txtName.setText("");
        txtID.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
    }
}