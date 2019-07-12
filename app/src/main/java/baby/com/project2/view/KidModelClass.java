package baby.com.project2.view;

public class KidModelClass {

    private String Id ,Image ,NickName , BirthDay,DateBirth;
    private int Gender;

    public KidModelClass(String id,String img ,String nickname ,String birthday,int gender,String dateBirth){
        this.Id = id;
        this.Image = img;
        this.NickName = nickname;
        this.BirthDay = birthday;
        this.Gender = gender;
        this.DateBirth = dateBirth;
    }

    public String getDateBirth() {
        return DateBirth;
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
