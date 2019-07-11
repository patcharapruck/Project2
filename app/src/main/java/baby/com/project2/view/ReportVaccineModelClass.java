package baby.com.project2.view;

public class ReportVaccineModelClass {

    private int Fk_id,V_id;
    private String Age,Status,Date,Nams,Place,Type;


    public ReportVaccineModelClass(int fk_id,int v_id,String name,String age , String date , String status,String place,String type){
        this.Fk_id = fk_id;
        this.V_id = v_id;
        this.Nams = name;
        this.Age = age;
        this.Status = status;
        this.Date = date;
        this.Place = place;
        this.Type = type;
    }

    public int getV_id() {
        return V_id;
    }

    public String getPlace() {
        return Place;
    }

    public int getFk_id() {
        return Fk_id;
    }

    public String getAge() {
        return Age;
    }

    public String getStatus() {
        return Status;
    }

    public String getDate() {
        return Date;
    }

    public String getType() {
        return Type;
    }

    public String getNams() {
        return Nams;
    }
}
