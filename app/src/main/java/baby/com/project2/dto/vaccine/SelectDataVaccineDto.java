package baby.com.project2.dto.vaccine;

import java.util.List;

public class SelectDataVaccineDto {

    private List<SelectDataVaccineItemsDto> datavaccine;


    public List<SelectDataVaccineItemsDto> getDatavaccine() {
        return datavaccine;
    }

    public void setDatavaccine(List<SelectDataVaccineItemsDto> datavaccine) {
        this.datavaccine = datavaccine;
    }
}
