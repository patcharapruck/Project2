package baby.com.project2.sentImage;

import com.google.gson.annotations.SerializedName;

public class Img_Pojo {


    @SerializedName("image_name")
    private String Title;

    @SerializedName("image")
    private String Image;

    @SerializedName("response")
    private boolean Response;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean getResponse() {
        return Response;
    }

    public void setResponse(boolean response) {
        Response = response;
    }
}
