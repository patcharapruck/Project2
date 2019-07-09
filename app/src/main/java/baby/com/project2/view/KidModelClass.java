package baby.com.project2.view;

public class KidModelClass {

    private String Id ,Image ,NickName , BirthDay;
    private int Gender;

    public KidModelClass(String id,String img ,String nickname ,String birthday,int gender){
        this.Id = id;
        this.Image = img;
        this.NickName = nickname;
        this.BirthDay = birthday;
        this.Gender = gender;
    }

    public String getId() {
        return Id;
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

    public int getGender() {
        return Gender;
    }
}
