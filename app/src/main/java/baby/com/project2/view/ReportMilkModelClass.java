package baby.com.project2.view;

public class ReportMilkModelClass {

    private int M_id;
    private String M_date,M_time,M_namefood,M_age,M_foodtype,M_volume;
    private int M_amount;

    public ReportMilkModelClass(int m_id, String m_date ,String m_time, String m_namefood,
                                String m_age,String m_foodtype,String m_volume,int m_amount){
        this.M_id = m_id;
        this.M_date = m_date;
        this.M_time = m_time;
        this.M_namefood = m_namefood;
        this.M_age = m_age;
        this.M_foodtype = m_foodtype;
        this.M_volume = m_volume;
        this.M_amount = m_amount;
    }

    public int getM_id() {
        return M_id;
    }

    public String getM_date() {
        return M_date;
    }

    public String getM_time() {
        return M_time;
    }

    public String getM_namefood() {
        return M_namefood;
    }

    public String getM_age() {
        return M_age;
    }

    public String getM_foodtype() {
        return M_foodtype;
    }

    public String getM_volume() {
        return M_volume;
    }

    public int getM_amount() {
        return M_amount;
    }
}
