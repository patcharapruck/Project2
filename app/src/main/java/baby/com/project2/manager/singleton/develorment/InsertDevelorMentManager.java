package baby.com.project2.manager.singleton.develorment;

import android.content.Context;

import baby.com.project2.dto.devlopment.InsertDevelorMentDto;
import baby.com.project2.dto.vaccine.InsertVaccineDto;
import baby.com.project2.manager.Contextor;

public class InsertDevelorMentManager {

    private static InsertDevelorMentManager instance;;
    private InsertDevelorMentDto itemsDto;
    private Context mContext;

    public static InsertDevelorMentManager getInstance() {
        if (instance == null)
            instance = new InsertDevelorMentManager();
        return instance;
    }

    private InsertDevelorMentManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public InsertDevelorMentDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(InsertDevelorMentDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
