package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.SizeDevDto;
import baby.com.project2.dto.devlopment.SelectAgeDevDto;
import baby.com.project2.manager.Contextor;

public class SizeDevManager {

    private static SizeDevManager instance;;
    private SizeDevDto itemsDto;
    private Context mContext;

    public static SizeDevManager getInstance() {
        if (instance == null)
            instance = new SizeDevManager();
        return instance;
    }

    private SizeDevManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SizeDevDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SizeDevDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
