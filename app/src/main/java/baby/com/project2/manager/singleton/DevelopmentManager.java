package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.devlopment.SelectDevDto;
import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;

public class DevelopmentManager {

    private static DevelopmentManager instance;;
    private SelectDevDto itemsDto;
    private Context mContext;

    public static DevelopmentManager getInstance() {
        if (instance == null)
            instance = new DevelopmentManager();
        return instance;
    }

    private DevelopmentManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectDevDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectDevDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
