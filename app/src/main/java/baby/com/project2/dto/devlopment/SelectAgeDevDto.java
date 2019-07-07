package baby.com.project2.dto.devlopment;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectAgeVaccineItemsDto;

public class SelectAgeDevDto {

    private List<SelectAgeDevItemsDto> age_dev;

    public List<SelectAgeDevItemsDto> getAge_dev() {
        return age_dev;
    }

    public void setAge_dev(List<SelectAgeDevItemsDto> age_dev) {
        this.age_dev = age_dev;
    }
}
