package ucr.ac.cr.boxb.ui.billing;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentSalesBinding;
import ucr.ac.cr.boxb.model.Bill;

public class salesFragment extends Fragment {

    FirebaseFirestore db;

    private FragmentSalesBinding binding;

    private ArrayList<Bill> listSales = new ArrayList<>();

    private Bill bill;

    private String clientId, type;

    public salesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentSalesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle args = getArguments();
//        if (!args.isEmpty()){
//            clientId = args.getString("clientId");
//            type = args.getString("type");
//        }

        System.out.println("type = " + type);

        // Fill the list of purchases
        fillList();
        // Return the root view
        return root;
    }

    private void addTable() {

        float density = getResources().getDisplayMetrics().density;

        int paddingInDp = 10;
        int paddingInPx = (int) (paddingInDp * density);

        //Table layout
        TableLayout stk = binding.tlFragmentSalesTableSales;
        //Table row
        TableRow tbRow0 = new TableRow(binding.getRoot().getContext());

        //Colors
        int color = binding.getRoot().getContext().getColor(R.color.soft_green);

        //Table headers
        TextView tvDate = new TextView(binding.getRoot().getContext());
        tvDate.setText(" Date ");
        tvDate.setTextColor(color);
        tvDate.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
        tbRow0.addView(tvDate);

        TextView tvName = new TextView(binding.getRoot().getContext());
        tvName.setText("Name");
        tvName.setTextColor(color);
        tvName.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
        tbRow0.addView(tvName);

        TextView tvAmount = new TextView(binding.getRoot().getContext());
        tvAmount.setText("Amount");
        tvAmount.setTextColor(color);
        tvAmount.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
        tbRow0.addView(tvAmount);

        TextView tvIVA = new TextView(binding.getRoot().getContext());
        tvIVA.setText("IVA");
        tbRow0.addView(tvIVA);
        tvIVA.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
        tvIVA.setTextColor(color);

        TextView tvTotal = new TextView(binding.getRoot().getContext());
        tvTotal.setText("TOTAL");
        tbRow0.addView(tvTotal);
        tvTotal.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
        tvTotal.setTextColor(color);

        stk.addView(tbRow0);

        GradientDrawable borderDrawable = new GradientDrawable();
        borderDrawable.setColor(color);
        borderDrawable.setStroke(2, color);


        for (Bill bill :
                listSales) {
            TableRow tbrow = new TableRow(binding.getRoot().getContext());

            TextView tvDateColumn = new TextView(binding.getRoot().getContext());
            tvDateColumn.setText(bill.getDate());
            tvDateColumn.setTextColor(color);
            tvDateColumn.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
            tbrow.addView(tvDateColumn);

            TextView tvNameColumn = new TextView(binding.getRoot().getContext());
            tvNameColumn.setText(bill.getName());
            tvNameColumn.setTextColor(color);
            tvNameColumn.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
            tbrow.addView(tvNameColumn);

            TextView tvAmountColumn = new TextView(binding.getRoot().getContext());
            tvAmountColumn.setText(bill.getAmount());
            tvAmountColumn.setTextColor(color);
            tvAmountColumn.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
            tbrow.addView(tvAmountColumn);


            TextView tvIVAColumn = new TextView(binding.getRoot().getContext());
            tvIVAColumn.setText(bill.getName());
            tvIVAColumn.setTextColor(color);
            tvIVAColumn.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
            tbrow.addView(tvIVAColumn);


            TextView tvTotalColumn = new TextView(binding.getRoot().getContext());
            tvTotalColumn.setText(bill.getName());
            tvTotalColumn.setTextColor(color);
            tvTotalColumn.setPadding(paddingInPx,paddingInPx,paddingInPx,paddingInPx);
            tbrow.addView(tvTotalColumn);


            stk.addView(tbrow);
        }
    }

    public void fillList(){

        listSales.clear();

        db.collection("Bills")
                .whereEqualTo("clientId", clientId)
                .whereEqualTo("type", type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            listClients.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(getContext(), "doc: " + document.getId(), Toast.LENGTH_SHORT).show();
////                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String idClient = document.getId();
                                String amount = document.getString("amount");
                                String clientId = document.getString("clientId");
                                String date = document.getString("date");
                                String iva = document.getString("iva");
                                String name = document.getString("name");
                                String type = document.getString("type");
                                bill = new Bill(clientId, name,type,iva,date,amount);
                                listSales.add(bill);
                            }
//                            clientAdapter = new ClientAdapter(getContext(), listClients);
//                            lstV_Home_listClients.setAdapter(clientAdapter);
                        } else {
                            Toast.makeText(getContext(), "Error obtaining data", Toast.LENGTH_LONG).show();
//                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        addTable();
                    }
                });


    }//fin listaDatos

}