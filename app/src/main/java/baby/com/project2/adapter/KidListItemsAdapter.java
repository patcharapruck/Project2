package baby.com.project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import baby.com.project2.R;
import baby.com.project2.activity.EditChildActivity;
import baby.com.project2.activity.HomeActivity;
import baby.com.project2.manager.singleton.child.SelectChildManager;
import baby.com.project2.util.SharedPrefUser;
import baby.com.project2.view.KidModelClass;

public class KidListItemsAdapter extends RecyclerView.Adapter<KidListItemsAdapter.CustomViewKidsList>{

    private Context context;
    private ArrayList<KidModelClass> items;

    public KidListItemsAdapter(Context context,ArrayList<KidModelClass> item){
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public CustomViewKidsList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewKidsList(LayoutInflater.from(context).inflate(R.layout.customview_kids_items, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewKidsList customViewKidsList, final int i) {

        if(items.get(i).getImage().length()<1||items.get(i).getImage()==null){
            if(items.get(i).getGender() == 1){
                customViewKidsList.ImageViewKids.setImageResource(R.mipmap.ic_baby_boy);
            }else {
                customViewKidsList.ImageViewKids.setImageResource(R.mipmap.ic_baby_gril);
            }
        }else{
            byte[] decodedString = Base64.decode(items.get(i).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            customViewKidsList.ImageViewKids.setImageBitmap(decodedByte);
        }

        customViewKidsList.TextViewNicknameKids.setText(items.get(i).getNickName());
        customViewKidsList.TextViewBirthday.setText(items.get(i).getBirthDay());
        customViewKidsList.ImageViewEditChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditChildActivity.class);
                intent.putExtra("Cid",i);
                context.startActivity(intent);
            }
        });

        customViewKidsList.CardViewListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefUser.getInstance(context)
                        .saveChidId(items.get(i).getId(),items.get(i).getGender(),items.get(i).getDateBirth());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SelectChildManager.getInstance().getItemsDto().getResult().size();
    }

    public class CustomViewKidsList extends RecyclerView.ViewHolder {

        TextView TextViewBirthday,TextViewNicknameKids;
        ImageView ImageViewKids,ImageViewEditChild;
        CardView CardViewListChild;

        public CustomViewKidsList(@NonNull View itemView) {
            super(itemView);
            TextViewNicknameKids = (TextView) itemView.findViewById(R.id.textview_nickname_kids);
            TextViewBirthday = (TextView) itemView.findViewById(R.id.textview_birthday);
            ImageViewKids = (ImageView) itemView.findViewById(R.id.image_view_kids);
            ImageViewEditChild = (ImageView) itemView.findViewById(R.id.imageview_edit_child);
            CardViewListChild = (CardView) itemView.findViewById(R.id.cardview_list_child);
        }
    }
}
