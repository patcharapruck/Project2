package baby.com.project2.manager.singleton.vaccine;

import android.content.Context;

import baby.com.project2.dto.growup.DeleteGrowUpDto;
import baby.com.project2.dto.vaccine.DeleteVaccineDto;
import baby.com.project2.manager.Contextor;

public class DeleteVacManager {

    private static DeleteVacManager instance;;
    private DeleteVaccineDto itemsDto;
    private Context mContext;

    public static DeleteVacManager getInstance() {
        if (instance == null)
            instance = new DeleteVacManager();
        return instance;
    }

    private DeleteVacManager() {
        mContext = Contextor.getInstance().getmContext();
    }

    public DeleteVaccineDto getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(DeleteVaccineDto itemsDto) {
        this.itemsDto = itemsDto;
    }
}
