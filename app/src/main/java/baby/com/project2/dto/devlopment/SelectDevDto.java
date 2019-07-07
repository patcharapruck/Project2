package baby.com.project2.dto.devlopment;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectListVaccineDto;

public class SelectDevDto {

    private List<SelectDevItemsDto> dev;

    public List<SelectDevItemsDto> getDev() {
        return dev;
    }

    public void setDev(List<SelectDevItemsDto> dev) {
        this.dev = dev;
    }
}
