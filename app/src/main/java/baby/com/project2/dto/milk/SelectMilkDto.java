package baby.com.project2.dto.milk;

import java.util.List;

import baby.com.project2.dto.child.SelectChildItemsDto;

public class SelectMilkDto {

    private List<SelectMilkItemsDto> milk;

    public List<SelectMilkItemsDto> getMilk() {
        return milk;
    }

    public void setMilk(List<SelectMilkItemsDto> milk) {
        this.milk = milk;
    }
}
