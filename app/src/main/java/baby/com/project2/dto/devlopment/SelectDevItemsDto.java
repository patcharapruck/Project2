package baby.com.project2.dto.devlopment;

public class SelectDevItemsDto {

    private String BD_id;
    private String BD_data;
    private String BD_image;
    private String id_agedev;
    private String id_type;

    public String getBD_id() {
        return BD_id;
    }

    public void setBD_id(String BD_id) {
        this.BD_id = BD_id;
    }

    public String getBD_data() {
        return BD_data;
    }

    public void setBD_data(String BD_data) {
        this.BD_data = BD_data;
    }

    public String getBD_image() {
        return BD_image;
    }

    public void setBD_image(String BD_image) {
        this.BD_image = BD_image;
    }

    public String getId_agedev() {
        return id_agedev;
    }

    public void setId_agedev(String id_agedev) {
        this.id_agedev = id_agedev;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }
}
