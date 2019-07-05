package baby.com.project2.view;

public class KidModelClass {

    private String Id ,Image ,NickName , BirthDay;

    public KidModelClass(String id,String img ,String nickname ,String birthday){
        this.Id = id;
        this.Image = img;
        this.NickName = nickname;
        this.BirthDay = birthday;
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
}
