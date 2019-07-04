package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.child.DeleteChildDto;
import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.manager.Contextor;

public class DeleteGrowManager {

    private static DeleteGrowManager instance;;
    private DeleteGrowUpDto itemsDto;
    private Context mContext;

    public static DeleteGrowManager getInstance() {
        if (instance == null)
            instance = new DeleteGrowManager();
        return instance;
    }

    private DeleteGrowManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public DeleteGrowUpDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(DeleteGrowUpDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
