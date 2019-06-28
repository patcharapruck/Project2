package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.RegisterDto;
import baby.com.project2.dto.child.UpdateChildDto;
import baby.com.project2.manager.Contextor;

public class RegisterManager {

    private static RegisterManager instance;;
    private RegisterDto itemsDto;
    private Context mContext;

    public static RegisterManager getInstance() {
        if (instance == null)
            instance = new RegisterManager();
        return instance;
    }

    private RegisterManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public RegisterDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(RegisterDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
