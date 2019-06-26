package baby.com.project2.dto;

public class LoginItemsDto {

    private String id;
    private String email;
    private String pass;
    private String name;
    private boolean connect;
    private boolean childchecked;
    private String comment = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public boolean isChildchecked() {
        return childchecked;
    }

    public void setChildchecked(boolean childchecked) {
        this.childchecked = childchecked;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
