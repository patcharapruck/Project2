package baby.com.project2.view;

public class IntroModelClass {

    private String Id_ageintro;
    private String Age_intro;

    public IntroModelClass(String id_ageintro, String age_intro){
        this.Id_ageintro = id_ageintro;
        this.Age_intro = age_intro;
    }

    public String getId_ageintro() {
        return Id_ageintro;
    }

    public String getAge_intro() {
        return Age_intro;
    }
}
