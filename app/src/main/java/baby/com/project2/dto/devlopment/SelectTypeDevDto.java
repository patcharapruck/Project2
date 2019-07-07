package baby.com.project2.dto.devlopment;

import java.util.List;

public class SelectTypeDevDto {

    private List<SelectTpyeDevItemsDto> type_dev;

    public List<SelectTpyeDevItemsDto> getTypeDev() {
        return type_dev;
    }

    public void setTypeDev(List<SelectTpyeDevItemsDto> dev) {
        this.type_dev = dev;
    }
}
