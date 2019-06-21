package baby.com.project2.view;

public class DevelopMentModelClass {

    private String Topics ,Side , Day;

    public DevelopMentModelClass(String topics , String side , String day){
        this.Topics = topics;
        this.Side = side;
        this.Day = day;
    }

    public String getTopics() {
        return Topics;
    }

    public String getSide() {
        return Side;
    }

    public String getDay() {
        return Day;
    }
}
