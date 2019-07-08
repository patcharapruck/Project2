package baby.com.project2.manager.singleton.child;

import android.content.Context;

import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.manager.Contextor;

public class SelectChildManager {

    private static SelectChildManager instance;;
    private SelectChildDto itemsDto;
    private Context mContext;

    public static SelectChildManager getInstance() {
        if (instance == null)
            instance = new SelectChildManager();
        return instance;
    }

    private SelectChildManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectChildDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectChildDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
