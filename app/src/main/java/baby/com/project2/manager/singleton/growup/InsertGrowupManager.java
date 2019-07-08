package baby.com.project2.manager.singleton.growup;

import android.content.Context;

import baby.com.project2.dto.growup.InsertGrowUpDto;
import baby.com.project2.manager.Contextor;

public class InsertGrowupManager {

    private static InsertGrowupManager instance;;
    private InsertGrowUpDto itemsDto;
    private Context mContext;

    public static InsertGrowupManager getInstance() {
        if (instance == null)
            instance = new InsertGrowupManager();
        return instance;
    }

    private InsertGrowupManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public InsertGrowUpDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(InsertGrowUpDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
