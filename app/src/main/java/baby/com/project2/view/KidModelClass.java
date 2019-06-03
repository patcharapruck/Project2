package baby.com.project2.view;

public class KidModelClass {

    private String Image ,NickName , BirthDay;

    public KidModelClass(String img ,String nickname ,String birthday){
        this.Image = img;
        this.NickName = nickname;
        this.BirthDay = birthday;
    }

    public String getImage() {
        return Image;
    }

    public String getNickName() {
        return NickName;
    }

    public String getBirthDay() {
        return BirthDay;
    }
}
