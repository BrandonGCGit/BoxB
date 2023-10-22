package ucr.ac.cr.boxb.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentGreetBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GreetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GreetFragment extends Fragment {

    private FragmentGreetBinding binding;
    NavController navController;
    Button btn_Greet_getStarted;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GreetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GreetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GreetFragment newInstance(String param1, String param2) {
        GreetFragment fragment = new GreetFragment();
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
        binding =  FragmentGreetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }//onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireView());
        btn_Greet_getStarted = binding.btnGreetGetStarted;

        btn_Greet_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_greetFragment_to_loginFragment);
            }
        });
    }
}