package baby.com.project2.dto.growup;

import java.util.List;

import baby.com.project2.dto.vaccine.SelectDataVaccineItemsDto;

public class SelectGrowUpDto {

    private List<SelectGrowItemsDto> growup;

    public List<SelectGrowItemsDto> getGrowup() {
        return growup;
    }

    public void setGrowup(List<SelectGrowItemsDto> growup) {
        this.growup = growup;
    }
}
