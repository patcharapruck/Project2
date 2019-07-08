package baby.com.project2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.adapter.IntroListAdapter;
import baby.com.project2.dto.intro.SelectAgeIntroDto;
import baby.com.project2.manager.http.HttpManager;
import baby.com.project2.manager.singleton.intro.AgeIntroManager;
import baby.com.project2.view.IntroModelClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListintroFragment extends Fragment {


    ArrayList<IntroModelClass> items;
    IntroListAdapter adapter;
    private RecyclerView recyclerView;

    SelectAgeIntroDto dtoage;
    public ListintroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listintro, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_list_intro);
        reqListIntro();
    }

    public void reqListIntro() {

        final Context mcontext = getContext();
        Call<SelectAgeIntroDto> call = HttpManager.getInstance().getService().loadAPIAgeIntroDtoCall();
        call.enqueue(new Callback<SelectAgeIntroDto>() {

            @Override
            public void onResponse(Call<SelectAgeIntroDto> call, Response<SelectAgeIntroDto> response) {
                if(response.isSuccessful()){
                    dtoage = response.body();
                    AgeIntroManager.getInstance().setItemsDto(dtoage);
                    setRecyclerView();

                }else {
                    Toast.makeText(mcontext,"เกิดข้อผิดพลาด",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SelectAgeIntroDto> call, Throwable t) {
                Toast.makeText(mcontext,t.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView() {

        Context context = getContext();
        items = new ArrayList<>();
        adapter = new IntroListAdapter(context, items);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        int size = dtoage.getAge_intro().size();

        for (int i = 0; i < size; i++) {
            try {
                items.add(new IntroModelClass(dtoage.getAge_intro().get(i).getId_ageintro()
                        ,dtoage.getAge_intro().get(i).getAge_intro()));
            }catch (ArrayIndexOutOfBoundsException e){
                break;
            }
            adapter.notifyDataSetChanged();
        }
    }

}
