package baby.com.project2.dto.devlopment;

import java.util.List;

public class SelectTypeDevDto {

    private List<SelectTypeDevItemsDto> type_dev;

    public List<SelectTypeDevItemsDto> getTypeDev() {
        return type_dev;
    }

    public void setTypeDev(List<SelectTypeDevItemsDto> dev) {
        this.type_dev = dev;
    }
}
