package baby.com.project2.dto.devlopment;

public class SelectDataDevItemsDto {

    private String FKcd_id;
    private String C_id;
    private String BD_id;
    private String FKcd_date;
    private int FKcd_status;

    public String getFKcd_id() {
        return FKcd_id;
    }

    public void setFKcd_id(String FKcd_id) {
        this.FKcd_id = FKcd_id;
    }

    public String getC_id() {
        return C_id;
    }

    public void setC_id(String c_id) {
        C_id = c_id;
    }

    public String getBD_id() {
        return BD_id;
    }

    public void setBD_id(String BD_id) {
        this.BD_id = BD_id;
    }

    public String getFKcd_date() {
        return FKcd_date;
    }

    public void setFKcd_date(String FKcd_date) {
        this.FKcd_date = FKcd_date;
    }

    public int getFKcd_status() {
        return FKcd_status;
    }

    public void setFKcd_status(int FKcd_status) {
        this.FKcd_status = FKcd_status;
    }
}
