package baby.com.project2.manager.singleton.vaccine;

import android.content.Context;

import baby.com.project2.dto.vaccine.InsertVaccineDto;
import baby.com.project2.manager.Contextor;

public class InsertVaccineManager {

    private static InsertVaccineManager instance;;
    private InsertVaccineDto itemsDto;
    private Context mContext;

    public static InsertVaccineManager getInstance() {
        if (instance == null)
            instance = new InsertVaccineManager();
        return instance;
    }

    private InsertVaccineManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public InsertVaccineDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(InsertVaccineDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
