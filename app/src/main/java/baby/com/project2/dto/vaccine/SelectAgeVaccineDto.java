package baby.com.project2.dto.vaccine;

import java.util.List;

public class SelectAgeVaccineDto {

    private List<SelectAgeVaccineItemsDto> age_vaccine;

    public List<SelectAgeVaccineItemsDto> getAge_vaccine() {
        return age_vaccine;
    }

    public void setAge_vaccine(List<SelectAgeVaccineItemsDto> age_vaccine) {
        this.age_vaccine = age_vaccine;
    }
}
