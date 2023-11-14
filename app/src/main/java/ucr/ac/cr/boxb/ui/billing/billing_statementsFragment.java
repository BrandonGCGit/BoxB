package ucr.ac.cr.boxb.ui.billing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import ucr.ac.cr.boxb.databinding.LytActBillingStatementBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link billing_statementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class billing_statementsFragment extends Fragment {

    private LytActBillingStatementBinding binding;
    NavController navController;
    Button btn_Greet_getStarted;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public billing_statementsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment billing_statementsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static billing_statementsFragment newInstance(String param1, String param2) {
        billing_statementsFragment fragment = new billing_statementsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding =  LytActBillingStatementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

}