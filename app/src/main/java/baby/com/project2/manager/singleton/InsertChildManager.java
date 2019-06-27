package baby.com.project2.manager.singleton;

import android.content.Context;
import baby.com.project2.dto.child.InsertChildDto;
import baby.com.project2.manager.Contextor;

public class InsertChildManager {

    private static InsertChildManager instance;;
    private InsertChildDto itemsDto;
    private Context mContext;

    public static InsertChildManager getInstance() {
        if (instance == null)
            instance = new InsertChildManager();
        return instance;
    }

    private InsertChildManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public InsertChildDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(InsertChildDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
