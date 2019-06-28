package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.dto.child.UpdateChildDto;
import baby.com.project2.manager.Contextor;

public class UpdateChildManager {

    private static UpdateChildManager instance;;
    private UpdateChildDto itemsDto;
    private Context mContext;

    public static UpdateChildManager getInstance() {
        if (instance == null)
            instance = new UpdateChildManager();
        return instance;
    }

    private UpdateChildManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public UpdateChildDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(UpdateChildDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
