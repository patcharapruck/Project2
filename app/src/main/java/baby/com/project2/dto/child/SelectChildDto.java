package baby.com.project2.dto.child;

import java.util.List;

public class SelectChildDto {

    private List<SelectChildItemsDto> result;

    public List<SelectChildItemsDto> getResult() {
        return result;
    }

    public void setResult(List<SelectChildItemsDto> result) {
        this.result = result;
    }
}
