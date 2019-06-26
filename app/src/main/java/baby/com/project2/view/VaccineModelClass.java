package baby.com.project2.view;

public class VaccineModelClass {

    private String Vaccine ,Type , Day;

    public VaccineModelClass(String vaccine , String type , String day){
        this.Vaccine = vaccine;
        this.Type = type;
        this.Day = day;
    }

    public String getVaccine() {
        return Vaccine;
    }

    public String getType() {
        return Type;
    }

    public String getDay() {
        return Day;
    }
}
