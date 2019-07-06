package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.child.DeleteChildDto;
import baby.com.project2.dto.milk.DeleteMilkDto;
import baby.com.project2.manager.Contextor;

public class DeleteMilkManager {

    private static DeleteMilkManager instance;;
    private DeleteMilkDto itemsDto;
    private Context mContext;

    public static DeleteMilkManager getInstance() {
        if (instance == null)
            instance = new DeleteMilkManager();
        return instance;
    }

    private DeleteMilkManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public DeleteMilkDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(DeleteMilkDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
