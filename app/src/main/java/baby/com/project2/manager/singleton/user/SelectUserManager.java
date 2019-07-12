package baby.com.project2.manager.singleton.user;

import android.content.Context;

import baby.com.project2.dto.milk.SelectMilkDto;
import baby.com.project2.dto.user.SelectUserDto;
import baby.com.project2.manager.Contextor;

public class SelectUserManager {

    private static SelectUserManager instance;;
    private SelectUserDto itemsDto;
    private Context mContext;

    public static SelectUserManager getInstance() {
        if (instance == null)
            instance = new SelectUserManager();
        return instance;
    }

    private SelectUserManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectUserDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectUserDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
