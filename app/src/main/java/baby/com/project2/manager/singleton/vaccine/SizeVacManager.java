package baby.com.project2.manager.singleton.vaccine;

import android.content.Context;

import baby.com.project2.dto.SizeDevDto;
import baby.com.project2.dto.SizeVacDto;
import baby.com.project2.manager.Contextor;

public class SizeVacManager {

    private static SizeVacManager instance;;
    private SizeVacDto itemsDto;
    private Context mContext;

    public static SizeVacManager getInstance() {
        if (instance == null)
            instance = new SizeVacManager();
        return instance;
    }

    private SizeVacManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SizeVacDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SizeVacDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
