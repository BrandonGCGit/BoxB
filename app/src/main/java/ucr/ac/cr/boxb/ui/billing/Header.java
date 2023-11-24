package ucr.ac.cr.boxb.ui.billing;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ucr.ac.cr.boxb.MainActivity;
import ucr.ac.cr.boxb.R;
import ucr.ac.cr.boxb.databinding.FragmentHeaderBinding;
import ucr.ac.cr.boxb.databinding.FragmentHomeBinding;

public class Header extends Fragment {

    private FragmentHeaderBinding binding;
    Button btn_Header_goBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//End onCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHeaderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }//End onCreateView

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_Header_goBack = binding.btnHeaderGoBack;

        btn_Header_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}