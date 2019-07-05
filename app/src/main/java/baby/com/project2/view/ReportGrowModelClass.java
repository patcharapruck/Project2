package baby.com.project2.view;

public class ReportGrowModelClass {

    private int G_id;
    private String G_date;
    private float G_height ,G_weigth;

    public ReportGrowModelClass(int g_id, float g_height ,float g_weigth,String g_date){
        this.G_id = g_id;
        this.G_date = g_date;
        this.G_height = g_height;
        this.G_weigth = g_weigth;
    }

    public int getG_id() {
        return G_id;
    }

    public String getG_date() {
        return G_date;
    }

    public float getG_height() {
        return G_height;
    }

    public float getG_weigth() {
        return G_weigth;
    }
}
