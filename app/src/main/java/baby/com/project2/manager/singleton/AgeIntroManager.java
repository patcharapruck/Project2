package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.intro.SelectAgeIntroDto;
import baby.com.project2.dto.vaccine.SelectAgeVaccineDto;
import baby.com.project2.manager.Contextor;

public class AgeIntroManager {

    private static AgeIntroManager instance;;
    private SelectAgeIntroDto itemsDto;
    private Context mContext;

    public static AgeIntroManager getInstance() {
        if (instance == null)
            instance = new AgeIntroManager();
        return instance;
    }

    private AgeIntroManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectAgeIntroDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectAgeIntroDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
