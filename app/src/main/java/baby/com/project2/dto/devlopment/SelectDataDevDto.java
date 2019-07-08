package baby.com.project2.dto.devlopment;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectDataVaccineItemsDto;

public class SelectDataDevDto {

    private List<SelectDataDevItemsDto> datadev;


    public List<SelectDataDevItemsDto> getDatadev() {
        return datadev;
    }

    public void setDatadev(List<SelectDataDevItemsDto> datadev) {
        this.datadev = datadev;
    }
}
