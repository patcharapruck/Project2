package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.LoginItemsDto;
import baby.com.project2.manager.Contextor;

public class LoginManager {

    private static LoginManager instance;;
    private LoginItemsDto itemsDto;
    private Context mContext;

    public static LoginManager getInstance() {
        if (instance == null)
            instance = new LoginManager();
        return instance;
    }

    private LoginManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public LoginItemsDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(LoginItemsDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
