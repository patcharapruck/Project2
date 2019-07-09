package baby.com.project2.manager.singleton.develorment;

import android.content.Context;

import baby.com.project2.dto.devlopment.SelectDataDevDto;
import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.manager.Contextor;

public class DataDevManager {

    private static DataDevManager instance;;
    private SelectDataDevDto itemsDto;
    private Context mContext;

    public static DataDevManager getInstance() {
        if (instance == null)
            instance = new DataDevManager();
        return instance;
    }

    private DataDevManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectDataDevDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectDataDevDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
