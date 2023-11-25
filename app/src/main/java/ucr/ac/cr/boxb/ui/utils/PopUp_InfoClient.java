package ucr.ac.cr.boxb.ui.utils;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import ucr.ac.cr.boxb.MainActivity;
import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.model.Client;
import ucr.ac.cr.boxb.ui.home.ClientAdapter;

public class PopUp_InfoClient {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference clienteRef;
    EditText txt_InfoClient_name, txt_InfoClient_lastname, txt_InfoClient_documentId, txt_InfoClient_email;
    Button btn_InfoClient_update, btn_InfoCliente_delete;

    FloatingActionButton btn_InfoClient_close;
    @SuppressLint("MissingInflatedId")
    public void showPopupInfoClient(Context context, String clientId) {
//        fragment_info_client
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_info_client, null);

        //Obtain the specified client
        clienteRef = db.collection("Clients").document(clientId);

        txt_InfoClient_name = view.findViewById(R.id.txt_InfoClient_name);
        txt_InfoClient_lastname = view.findViewById(R.id.txt_InfoClient_lastname);
        txt_InfoClient_documentId = view.findViewById(R.id.txt_InfoClient_documentId);
        txt_InfoClient_email = view.findViewById(R.id.txt_InfoClient_email);

        btn_InfoClient_update = view.findViewById(R.id.btn_InfoClient_update);
        btn_InfoCliente_delete = view.findViewById(R.id.btn_InfoClient_delete);
        btn_InfoClient_close = view.findViewById(R.id.btn_InfoClient_close);


        clienteRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        Toast.makeText(context, "DocumentSnapshot data: " + document.getData(), Toast.LENGTH_SHORT).show();
                        txt_InfoClient_name.setText(document.getString("name"));
                        txt_InfoClient_lastname.setText(document.getString("lastName"));
                        txt_InfoClient_documentId.setText(document.getString("documentID"));
                        txt_InfoClient_email.setText(document.getString("email"));
                    } else {
                        Toast.makeText(context, "No such document", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "get failed with "+ task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Build AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        btn_InfoCliente_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteRef.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Client deleted", Toast.LENGTH_SHORT).show();
                                // Delete the bills associated with that client
                                deleteBillsWithClientId(context, clientId);
                                Intent intent = new Intent(context, MainActivity.class);
                                startActivity(context, intent, null);
                                alertDialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Error trying to delete", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btn_InfoClient_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fillAllBlanks()){
                    //New values
                    String newName = txt_InfoClient_name.getText().toString().trim();
                    String newLastName = txt_InfoClient_lastname.getText().toString();
                    String newDocumentId = txt_InfoClient_documentId.getText().toString().trim();
                    String newEmail = txt_InfoClient_email.getText().toString().trim();

                    //Create new Array
                    Map<String, Object> updatedData = new HashMap<>();
                    updatedData.put("name", newName);
                    updatedData.put("lastName", newLastName);
                    updatedData.put("documentID", newDocumentId);
                    updatedData.put("email", newEmail);

                    // Update info on FB
                    clienteRef.update(updatedData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Information update correctly", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(context, intent, null);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Error couldn't update", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        btn_InfoClient_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }//End showPopupInfoClient


    private void deleteBillsWithClientId(Context context, String clientId) {

        db.collection("Bills")
                .whereEqualTo("clientId", clientId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                document.getReference().delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Toast.makeText(context, "Bills deleted", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Couldn't delete bills", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(context, "No such document found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean fillAllBlanks(){
        String ClientName = txt_InfoClient_name.getText().toString();
        String ClientLastname = txt_InfoClient_lastname.getText().toString();
        String ClientDocumentId = txt_InfoClient_documentId.getText().toString();
        String ClientEmail = txt_InfoClient_email.getText().toString();

        if (ClientName.isEmpty()){
            txt_InfoClient_name.setError("Required");
            return false;
        }else if (ClientLastname.isEmpty()) {
            txt_InfoClient_lastname.setError("Required");
            return false;
        }else if (ClientDocumentId.isEmpty()) {
            txt_InfoClient_documentId.setError("Required");
            return false;
        }else if (ClientEmail.isEmpty()) {
            txt_InfoClient_email.setError("Required");
            return false;
        }else{
            return true;
        }

    }//End checkBlanks


}//End Class
