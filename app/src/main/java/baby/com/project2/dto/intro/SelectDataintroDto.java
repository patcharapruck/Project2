package baby.com.project2.dto.intro;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectDataVaccineItemsDto;

public class SelectDataintroDto {

    private List<SelectDataIntroItemsDto> dataintro;

    public List<SelectDataIntroItemsDto> getDataintro() {
        return dataintro;
    }

    public void setDataintro(List<SelectDataIntroItemsDto> dataintro) {
        this.dataintro = dataintro;
    }
}
