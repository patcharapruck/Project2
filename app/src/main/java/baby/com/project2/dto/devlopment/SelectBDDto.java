package baby.com.project2.dto.devlopment;

import java.util.List;

public class SelectBDDto {

    private List<SelectBDItemsDto> BD;

    public List<SelectBDItemsDto> getBD() {
        return BD;
    }

    public void setBD(List<SelectBDItemsDto> BD) {
        this.BD = BD;
    }
}
