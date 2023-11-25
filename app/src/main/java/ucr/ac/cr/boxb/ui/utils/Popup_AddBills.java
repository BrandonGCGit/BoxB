package ucr.ac.cr.boxb.ui.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ucr.ac.cr.boxb.R;


public class Popup_AddBills {

    private OnBillAddedListener listener;

    public Popup_AddBills(OnBillAddedListener listener){
        this.listener = listener;
    }

    EditText txt_name_add_bill, txt_date_add_bill, txt_amount_add_bill, txt_iva_add_bill;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void showPopupAddBill(Context context, String clientId) {
        // Infla el diseño personalizado
        View view = LayoutInflater.from(context).inflate(R.layout.lyt_add_bill, null);

        txt_name_add_bill = view.findViewById(R.id.txt_name_add_bill);
        txt_date_add_bill = view.findViewById(R.id.txt_date_add_bill);
        txt_amount_add_bill = view.findViewById(R.id.txt_amount_add_bill);
        txt_iva_add_bill = view.findViewById(R.id.txt_iva_add_bill);

        Spinner spn_type_add_bill = view.findViewById(R.id.spn_type_add_bill);

        Button btn_add_bill = view.findViewById(R.id.btn_add_bill);


        // Construye el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        // Configura cualquier otra configuración que desees


        // Muestra el AlertDialog
        AlertDialog alertDialog = builder.create();

        btn_add_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fillAllBlanks()){
                    // Add a new document with a generated id.
                    Map<String, Object> bill = new HashMap<>();
                    bill.put("clientId", clientId);
                    bill.put("name", txt_name_add_bill.getText().toString());
                    bill.put("date", txt_date_add_bill.getText().toString());
                    bill.put("amount", txt_amount_add_bill.getText().toString());
                    bill.put("iva", txt_iva_add_bill.getText().toString());
                    bill.put("type", spn_type_add_bill.getSelectedItem().toString());

                    db.collection("Bills")
                            .add(bill)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(v.getContext(), "Bill added", Toast.LENGTH_SHORT).show();
                                    alertDialog.hide();

                                    if (listener != null)
                                    {
                                        listener.onBillAdded();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Could not add bill", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        alertDialog.show();
    }//End showPopupAddBill


    public boolean fillAllBlanks(){
        String billName = txt_name_add_bill.getText().toString();
        String billDate = txt_date_add_bill.getText().toString();
        String billAmount = txt_amount_add_bill.getText().toString();
        String billIva = txt_iva_add_bill.getText().toString();

        if (billName.isEmpty()){
            txt_name_add_bill.setError("Required");
            return false;
        }else if (billDate.isEmpty()) {
            txt_date_add_bill.setError("Required");
            return false;
        }else if (billAmount.isEmpty()) {
            txt_amount_add_bill.setError("Required");
            return false;
        }else if (billIva.isEmpty()) {
            txt_iva_add_bill.setError("Required");
            return false;
        }else{
            return true;
        }

    }//End checkBlanks

    public interface OnBillAddedListener{
        void onBillAdded();
    }
}//End class
