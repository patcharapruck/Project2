package baby.com.project2.manager.singleton.vaccine;

import android.content.Context;

import baby.com.project2.dto.vaccine.SelectVaccineDto;
import baby.com.project2.manager.Contextor;

public class VaccineManager {

    private static VaccineManager instance;;
    private SelectVaccineDto itemsDto;
    private Context mContext;

    public static VaccineManager getInstance() {
        if (instance == null)
            instance = new VaccineManager();
        return instance;
    }

    private VaccineManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectVaccineDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectVaccineDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
