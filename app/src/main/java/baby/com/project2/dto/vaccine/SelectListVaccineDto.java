package baby.com.project2.dto.vaccine;

public class SelectListVaccineDto {

    private String V_id;
    private String V_name;
    private String V_type;
    private String id_agevac;

    public String getV_id() {
        return V_id;
    }

    public void setV_id(String v_id) {
        V_id = v_id;
    }

    public String getV_name() {
        return V_name;
    }

    public void setV_name(String v_name) {
        V_name = v_name;
    }

    public String getV_type() {
        return V_type;
    }

    public void setV_type(String v_type) {
        V_type = v_type;
    }

    public String getId_agevac() {
        return id_agevac;
    }

    public void setId_agevac(String id_agevac) {
        this.id_agevac = id_agevac;
    }
}
