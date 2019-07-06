package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.child.UpdateChildDto;
import baby.com.project2.dto.milk.UpdateMilkDto;
import baby.com.project2.manager.Contextor;

public class UpdateMilkManager {

    private static UpdateMilkManager instance;;
    private UpdateMilkDto itemsDto;
    private Context mContext;

    public static UpdateMilkManager getInstance() {
        if (instance == null)
            instance = new UpdateMilkManager();
        return instance;
    }

    private UpdateMilkManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public UpdateMilkDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(UpdateMilkDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
