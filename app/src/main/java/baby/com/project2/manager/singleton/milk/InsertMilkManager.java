package baby.com.project2.manager.singleton.milk;

import android.content.Context;

import baby.com.project2.dto.milk.InsertMilkDto;
import baby.com.project2.manager.Contextor;

public class InsertMilkManager {

    private static InsertMilkManager instance;;
    private InsertMilkDto itemsDto;
    private Context mContext;

    public static InsertMilkManager getInstance() {
        if (instance == null)
            instance = new InsertMilkManager();
        return instance;
    }

    private InsertMilkManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public InsertMilkDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(InsertMilkDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
