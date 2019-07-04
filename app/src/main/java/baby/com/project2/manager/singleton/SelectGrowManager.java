package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.child.SelectChildDto;
import baby.com.project2.dto.growup.SelectGrowUpDto;
import baby.com.project2.manager.Contextor;

public class SelectGrowManager {

    private static SelectGrowManager instance;;
    private SelectGrowUpDto itemsDto;
    private Context mContext;

    public static SelectGrowManager getInstance() {
        if (instance == null)
            instance = new SelectGrowManager();
        return instance;
    }

    private SelectGrowManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectGrowUpDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectGrowUpDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
