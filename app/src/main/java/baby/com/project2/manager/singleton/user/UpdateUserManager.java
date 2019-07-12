package baby.com.project2.manager.singleton.user;

import android.content.Context;

import baby.com.project2.dto.milk.UpdateMilkDto;
import baby.com.project2.dto.user.UpdateUserDto;
import baby.com.project2.manager.Contextor;

public class UpdateUserManager {

    private static UpdateUserManager instance;;
    private UpdateUserDto itemsDto;
    private Context mContext;

    public static UpdateUserManager getInstance() {
        if (instance == null)
            instance = new UpdateUserManager();
        return instance;
    }

    private UpdateUserManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public UpdateUserDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(UpdateUserDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
