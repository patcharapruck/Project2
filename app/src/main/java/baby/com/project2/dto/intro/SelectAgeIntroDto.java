package baby.com.project2.dto.intro;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectAgeVaccineItemsDto;

public class SelectAgeIntroDto {

    private List<SelectAgeIntroItemsDto> age_intro;

    public List<SelectAgeIntroItemsDto> getAge_intro() {
        return age_intro;
    }

    public void setAge_intro(List<SelectAgeIntroItemsDto> age_intro) {
        this.age_intro = age_intro;
    }
}
