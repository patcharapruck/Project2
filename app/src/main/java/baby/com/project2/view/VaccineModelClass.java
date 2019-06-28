package baby.com.project2.view;

public class VaccineModelClass {

    private String Vaccine ,Type;

    public VaccineModelClass(String vaccine , String type){
        this.Vaccine = vaccine;
        this.Type = type;
    }

    public String getVaccine() {
        return Vaccine;
    }

    public String getType() {
        return Type;
    }

}
