package baby.com.project2.view;

public class DevelopMentDataModelClass {

    private String BD_id;
    private String BD_data;
    private String BD_image;
    private String Id_agedev;
    private String Id_type;

    public DevelopMentDataModelClass(String bD_id , String bD_data,String bD_image,String id_agedev,String id_type){
        this.BD_id = bD_id;
        this.BD_data = bD_data;
        this.BD_image = bD_image;
        this.Id_agedev = id_agedev;
        this.Id_type = id_type;
    }

    public String getBD_id() {
        return BD_id;
    }

    public String getBD_data() {
        return BD_data;
    }

    public String getBD_image() {
        return BD_image;
    }

    public String getId_agedev() {
        return Id_agedev;
    }

    public String getId_type() {
        return Id_type;
    }
}
