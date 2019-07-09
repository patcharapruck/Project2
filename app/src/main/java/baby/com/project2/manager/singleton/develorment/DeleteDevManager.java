package baby.com.project2.manager.singleton.develorment;

import android.content.Context;

import baby.com.project2.dto.devlopment.DeleteDevelorMentDto;
import baby.com.project2.dto.vaccine.DeleteVaccineDto;
import baby.com.project2.manager.Contextor;

public class DeleteDevManager {

    private static DeleteDevManager instance;;
    private DeleteDevelorMentDto itemsDto;
    private Context mContext;

    public static DeleteDevManager getInstance() {
        if (instance == null)
            instance = new DeleteDevManager();
        return instance;
    }

    private DeleteDevManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public DeleteDevelorMentDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(DeleteDevelorMentDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
