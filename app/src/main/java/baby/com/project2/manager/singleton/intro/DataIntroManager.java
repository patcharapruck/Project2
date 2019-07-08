package baby.com.project2.manager.singleton.intro;

import android.content.Context;

import baby.com.project2.dto.intro.SelectDataintroDto;
import baby.com.project2.manager.Contextor;

public class DataIntroManager {

    private static DataIntroManager instance;;
    private SelectDataintroDto itemsDto;
    private Context mContext;

    public static DataIntroManager getInstance() {
        if (instance == null)
            instance = new DataIntroManager();
        return instance;
    }

    private DataIntroManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public SelectDataintroDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(SelectDataintroDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
