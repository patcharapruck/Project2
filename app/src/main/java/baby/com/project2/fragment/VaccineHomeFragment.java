package baby.com.project2.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import baby.com.project2.R;
import baby.com.project2.activity.DevelopMentActivity;
import baby.com.project2.activity.VaccineActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VaccineHomeFragment extends Fragment implements View.OnClickListener {

    private Button ButtonAddData;
    private TextView TextViewStatusVaccine;

    public VaccineHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_vaccine_home, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        ButtonAddData         = (Button)rootView.findViewById(R.id.button_add_data);
        TextViewStatusVaccine = (TextView)rootView.findViewById(R.id.textview_status_vaccine);

        ButtonAddData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), VaccineActivity.class);
        getContext().startActivity(intent);
    }
}
