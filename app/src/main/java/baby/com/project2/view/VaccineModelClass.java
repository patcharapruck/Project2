package baby.com.project2.view;

public class VaccineModelClass {

    private String v_id;
    private String Vaccine ,Type;

    public VaccineModelClass(String id,String vaccine , String type){
        this.v_id = id;
        this.Vaccine = vaccine;
        this.Type = type;
    }

    public String getV_id() {
        return v_id;
    }
    public String getVaccine() {
        return Vaccine;
    }

    public String getType() {
        return Type;
    }

}
