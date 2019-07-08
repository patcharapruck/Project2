package baby.com.project2.manager.singleton.vaccine;

import android.content.Context;

import baby.com.project2.dto.vaccine.SelectDataVaccineDto;
import baby.com.project2.manager.Contextor;

public class DataVaccineManager {

    private static DataVaccineManager instance;;
    private SelectDataVaccineDto itemsDto;
    private Context mContext;

    public static DataVaccineManager getInstance() {
        if (instance == null)
            instance = new DataVaccineManager();
        return instance;
    }

    private DataVaccineManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectDataVaccineDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectDataVaccineDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
