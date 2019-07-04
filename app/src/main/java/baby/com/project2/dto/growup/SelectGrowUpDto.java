package baby.com.project2.dto.growup;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectDataVaccineItemsDto;

public class SelectGrowUpDto {

    private List<SelectGrowItemsDto> datavaccine;

    public List<SelectGrowItemsDto> getDatavaccine() {
        return datavaccine;
    }

    public void setDatavaccine(List<SelectGrowItemsDto> datavaccine) {
        this.datavaccine = datavaccine;
    }
}
