package baby.com.project2.view;

public class ReportDevModelClass {

    private String Id,BD_id;
    private String Date;
    private String Type ,Age,Name;
    private int Status;

    public ReportDevModelClass(String id,String bd_id ,String date,String type ,String age, String name,int status){
        this.Id = id;
        this.BD_id = bd_id;
        this.Date = date;
        this.Type = type;
        this.Age = age;
        this.Name = name;
        this.Status = status;
    }

    public String getId() {
        return Id;
    }

    public String getDate() {
        return Date;
    }

    public String getType() {
        return Type;
    }

    public String getAge() {
        return Age;
    }

    public String getName() {
        return Name;
    }

    public int getStatus() {
        return Status;
    }

    public String getBD_id() {
        return BD_id;
    }
}
