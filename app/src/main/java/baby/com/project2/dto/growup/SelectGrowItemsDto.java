package baby.com.project2.dto.growup;

public class SelectGrowItemsDto {

    private int G_id;
    private float G_height;
    private float G_weight;
    private String G_date;
    private int C_id;

    public int getG_id() {
        return G_id;
    }

    public void setG_id(int g_id) {
        G_id = g_id;
    }

    public float getG_height() {
        return G_height;
    }

    public void setG_height(float g_height) {
        G_height = g_height;
    }

    public float getG_weight() {
        return G_weight;
    }

    public void setG_weight(float g_weight) {
        G_weight = g_weight;
    }

    public String getG_date() {
        return G_date;
    }

    public void setG_date(String g_date) {
        G_date = g_date;
    }

    public int getC_id() {
        return C_id;
    }

    public void setC_id(int c_id) {
        C_id = c_id;
    }
}
