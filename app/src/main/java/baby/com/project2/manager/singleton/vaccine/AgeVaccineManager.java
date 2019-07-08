package baby.com.project2.manager.singleton.vaccine;

import android.content.Context;

import baby.com.project2.dto.vaccine.SelectAgeVaccineDto;
import baby.com.project2.manager.Contextor;

public class AgeVaccineManager {

    private static AgeVaccineManager instance;;
    private SelectAgeVaccineDto itemsDto;
    private Context mContext;

    public static AgeVaccineManager getInstance() {
        if (instance == null)
            instance = new AgeVaccineManager();
        return instance;
    }

    private AgeVaccineManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectAgeVaccineDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectAgeVaccineDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
