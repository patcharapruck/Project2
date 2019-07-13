package baby.com.project2.view;

public class IntroDataModelClass {

    private String I_id;
    private String I_data;
    private String I_image;

    public IntroDataModelClass(String i_id, String i_data,String i_image){
        this.I_id = i_id;
        this.I_data = i_data;
        this.I_image = i_image;
    }

    public String getI_id() {
        return I_id;
    }

    public String getI_data() {
        return I_data;
    }

    public String getI_image() {
        return I_image;
    }
}
