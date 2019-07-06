package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.dto.milk.SelectMilkDto;
import baby.com.project2.manager.Contextor;

public class SelectMilkManager {

    private static SelectMilkManager instance;;
    private SelectMilkDto itemsDto;
    private Context mContext;

    public static SelectMilkManager getInstance() {
        if (instance == null)
            instance = new SelectMilkManager();
        return instance;
    }

    private SelectMilkManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectMilkDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectMilkDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
