package baby.com.project2.manager.singleton.child;

import android.content.Context;

import baby.com.project2.dto.child.DeleteChildDto;
import baby.com.project2.manager.Contextor;

public class DeleteChildManager {

    private static DeleteChildManager instance;;
    private DeleteChildDto itemsDto;
    private Context mContext;

    public static DeleteChildManager getInstance() {
        if (instance == null)
            instance = new DeleteChildManager();
        return instance;
    }

    private DeleteChildManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public DeleteChildDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(DeleteChildDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
