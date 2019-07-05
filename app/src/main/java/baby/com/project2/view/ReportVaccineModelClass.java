package baby.com.project2.view;

public class ReportVaccineModelClass {

    private int Fk_id;
    private String Age,Status,Date,Nams;


    public ReportVaccineModelClass(int fk_id,String name,String age , String date , String status){
        this.Fk_id = fk_id;
        this.Nams = name;
        this.Age = age;
        this.Status = status;
        this.Date = date;
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


    public String getNams() {
        return Nams;
    }
}
