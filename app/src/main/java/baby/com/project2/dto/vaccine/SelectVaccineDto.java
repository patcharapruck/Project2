package baby.com.project2.dto.vaccine;

import java.util.List;

public class SelectVaccineDto {

    private List<SelectListVaccineDto> vaccine;

    public List<SelectListVaccineDto> getVaccine() {
        return vaccine;
    }

    public void setVaccine(List<SelectListVaccineDto> vaccine) {
        this.vaccine = vaccine;
    }
}
