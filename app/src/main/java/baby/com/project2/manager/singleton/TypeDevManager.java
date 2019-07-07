package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.devlopment.SelectAgeDevDto;
import baby.com.project2.dto.devlopment.SelectTypeDevDto;
import baby.com.project2.manager.Contextor;

public class TypeDevManager {

    private static TypeDevManager instance;;
    private SelectTypeDevDto itemsDto;
    private Context mContext;

    public static TypeDevManager getInstance() {
        if (instance == null)
            instance = new TypeDevManager();
        return instance;
    }

    private TypeDevManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectTypeDevDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectTypeDevDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
