package ucr.ac.cr.boxb.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    Spinner spinner, spinner1;

    RequestQueue requestQueue;

    CardView btnConversor;

    TextView txtFrom, txtTo;


    String divisa1, divisa2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initComponents();

        iniUI();

        initListeners();



        return root;
    }

    private void initListeners() {
        btnConversor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "https://api.cambio.today/v1/quotes/"+divisa1+"/"+divisa2+"/json?quantity="+txtFrom.getText()+"&key=45816|GOG4CB4u5GnqVvyTmR3m";
                //jsonArrayRequest();
                //stringRequest();
                jsonObjectRequest(URL);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisa1= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisa2= parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void iniUI() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.divisas, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.divisas, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter1);


        requestQueue = Volley.newRequestQueue(binding.getRoot().getContext());
    }

    private void initComponents() {
        spinner = binding.spinner;
        spinner1 = binding.spinner2;

        btnConversor = binding.cvConvertor;

        txtFrom = binding.txtFrom;
        txtTo = binding.txtTo;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void jsonObjectRequest(String URL){
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray jsonArray = response.getJSONArray("result");
                            //int size = jsonArray.length();
                            //for (int i=0; i<size; i++){
                            JSONObject jsonObject = new JSONObject(String.valueOf(response.getJSONObject("result")));
                            String amount = jsonObject.getString("amount");
                            txtTo.setText(amount);

                            //}
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


}