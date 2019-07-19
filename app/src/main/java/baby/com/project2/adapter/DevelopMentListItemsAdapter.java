package baby.com.project2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.dto.SizeDevDto;
import baby.com.project2.dto.devlopment.SelectDevDto;
import baby.com.project2.manager.Contextor;
import baby.com.project2.manager.singleton.develorment.DevelopmentManager;
import baby.com.project2.manager.singleton.develorment.SizeDevManager;
import baby.com.project2.manager.singleton.develorment.TypeDevManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.DevelopMentDataModelClass;
import baby.com.project2.view.DevelopMentModelClass;

public class DevelopMentListItemsAdapter extends RecyclerView.Adapter<DevelopMentListItemsAdapter.CustomViewDevelopMentList> {

    private Context context;
    private ArrayList<DevelopMentModelClass> items;


    public DevelopMentListItemsAdapter(Context context, ArrayList<DevelopMentModelClass> item) {
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewDevelopMentList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewDevelopMentList(LayoutInflater.from(context).inflate(R.layout.customview_list_development, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewDevelopMentList customViewDevelopMentList, int i) {

        customViewDevelopMentList.Devitems.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        customViewDevelopMentList.Devitems.setAdapter(customViewDevelopMentList.adapter);
        customViewDevelopMentList.TextViewType.setText(items.get(i).getData());
        customViewDevelopMentList.Mycontent.collapse();
        customViewDevelopMentList.CardViewDevelopment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewDevelopMentList.Mycontent.toggle();
            }
        });

        ArrayList<String> stringArrayList1 = Dac();

        SelectDevDto devDto = DevelopmentManager.getInstance().getItemsDto();


        int size = 0, sizedev = 0;
        if (devDto.getDev() == null)
            size = 0;
        else
            size = devDto.getDev().size();

        int sum = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrithint();

        if (sum >= 19) {
            for (int j = 0; j < size; j++) {
                if (devDto.getDev().get(j).getId_type().equals(items.get(i).getId())) {
                    sizedev++;
                    try {
                        customViewDevelopMentList.items.add(new DevelopMentDataModelClass(devDto.getDev().get(j).getBD_id()
                                , devDto.getDev().get(j).getBD_data()
                                , devDto.getDev().get(j).getBD_image()
                                , devDto.getDev().get(j).getId_agedev()
                                , items.get(i).getData()));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        break;
                    }
                    customViewDevelopMentList.adapter.notifyDataSetChanged();
                }
            }
            SizeDevDto sizeDevDto = new SizeDevDto();
            sizeDevDto.setSize_dev(sizedev);
            SizeDevManager.getInstance().setItemsDto(sizeDevDto);

        } else {
            int sizeType = stringArrayList1.size();
            for (int j = 0; j < sizeType; j++) {
                for (int k = 0; k < size; k++) {
                    if (devDto.getDev().get(k).getId_agedev().equals(stringArrayList1.get(j))) {
                        if(devDto.getDev().get(k).getId_type().equals(items.get(i).getId())){
                            sizedev++;
                            try {
                                customViewDevelopMentList.items.add(new DevelopMentDataModelClass(devDto.getDev().get(k).getBD_id()
                                        ,devDto.getDev().get(k).getBD_data()
                                        ,devDto.getDev().get(k).getBD_image()
                                        ,devDto.getDev().get(k).getId_agedev()
                                        ,items.get(i).getData()));
                            }catch (ArrayIndexOutOfBoundsException e){
                                break;
                            }
                            customViewDevelopMentList.adapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            SizeDevDto sizeDevDto = new SizeDevDto();
            sizeDevDto.setSize_dev(sizedev);
            SizeDevManager.getInstance().setItemsDto(sizeDevDto);
        }


    }

    private ArrayList<String> Dac() {

        ArrayList<String> stringArrayList = new ArrayList<>();
        int sum = SharedPrefUser.getInstance(Contextor.getInstance().getmContext()).getKeyBrithint();

        if (sum < 2) {
            stringArrayList.add("01");
        } else if (sum < 3) {
            stringArrayList.add("01");
            stringArrayList.add("02");
        } else if (sum < 5) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
        } else if (sum < 7) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
        } else if (sum < 9) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
        } else if (sum < 10) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
        } else if (sum < 13) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
        } else if (sum < 16) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("08");
        } else if (sum < 18) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("08");
            stringArrayList.add("09");
        } else if (sum < 19) {
            stringArrayList.add("01");
            stringArrayList.add("02");
            stringArrayList.add("03");
            stringArrayList.add("04");
            stringArrayList.add("05");
            stringArrayList.add("06");
            stringArrayList.add("07");
            stringArrayList.add("08");
            stringArrayList.add("09");
            stringArrayList.add("10");
        }
        return stringArrayList;
    }

    @Override
    public int getItemCount() {
        return TypeDevManager.getInstance().getItemsDto().getTypeDev().size();
    }

    public class CustomViewDevelopMentList extends RecyclerView.ViewHolder {

        TextView TextViewType;
        CardView CardViewDevelopment;
        ExpandableRelativeLayout Mycontent;
        RecyclerView Devitems;
        ArrayList<DevelopMentDataModelClass> items;
        DevelopMentItemsAdapter adapter;


        public CustomViewDevelopMentList(@NonNull View itemView) {
            super(itemView);

            TextViewType = (TextView) itemView.findViewById(R.id.textview_type);
            CardViewDevelopment = (CardView) itemView.findViewById(R.id.cardview_development);
            Mycontent = (ExpandableRelativeLayout) itemView.findViewById(R.id.mycontent);
            Devitems = (RecyclerView) itemView.findViewById(R.id.devitems);
            items = new ArrayList<>();
            adapter = new DevelopMentItemsAdapter(context, items);

        }
    }
}
