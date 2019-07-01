package baby.com.project2.dto.vaccine;

public class SelectDataVaccineItemsDto {

    private int FKcv_id;
    private int C_id;
    private int V_id;
    private String FKcv_date;
    private int FKcv_status;
    private String FKcv_plase;

    public int getFKcv_id() {
        return FKcv_id;
    }

    public void setFKcv_id(int FKcv_id) {
        this.FKcv_id = FKcv_id;
    }

    public int getC_id() {
        return C_id;
    }

    public void setC_id(int c_id) {
        C_id = c_id;
    }

    public int getV_id() {
        return V_id;
    }

    public void setV_id(int v_id) {
        V_id = v_id;
    }

    public String getFKcv_date() {
        return FKcv_date;
    }

    public void setFKcv_date(String FKcv_date) {
        this.FKcv_date = FKcv_date;
    }

    public int getFKcv_status() {
        return FKcv_status;
    }

    public void setFKcv_status(int FKcv_status) {
        this.FKcv_status = FKcv_status;
    }

    public String getFKcv_plase() {
        return FKcv_plase;
    }

    public void setFKcv_plase(String FKcv_plase) {
        this.FKcv_plase = FKcv_plase;
    }
}
