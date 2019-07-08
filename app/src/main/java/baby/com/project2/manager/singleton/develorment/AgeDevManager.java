package baby.com.project2.manager.singleton.develorment;

import android.content.Context;

import baby.com.project2.dto.devlopment.SelectAgeDevDto;
import baby.com.project2.manager.Contextor;

public class AgeDevManager {

    private static AgeDevManager instance;;
    private SelectAgeDevDto itemsDto;
    private Context mContext;

    public static AgeDevManager getInstance() {
        if (instance == null)
            instance = new AgeDevManager();
        return instance;
    }

    private AgeDevManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectAgeDevDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectAgeDevDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
