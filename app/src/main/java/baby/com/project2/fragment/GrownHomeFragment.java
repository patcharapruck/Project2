package baby.com.project2.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import baby.com.project2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrownHomeFragment extends Fragment {


    public GrownHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grown_home, container, false);
    }

}
