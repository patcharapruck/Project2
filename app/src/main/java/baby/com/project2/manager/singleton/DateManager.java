package baby.com.project2.manager.singleton;

import android.content.Context;

import baby.com.project2.dto.DateDto;
import baby.com.project2.manager.Contextor;

public class DateManager {

    private static DateManager instance;

    private DateDto dateDto;

    public static DateManager getInstance() {
        if (instance == null)
            instance = new DateManager();
        return instance;
    }

    public DateDto getDateDto() {
        return dateDto;
    }

    public void setDateDto(DateDto dateDto) {
        this.dateDto = dateDto;
    }

    private Context mContext;

    private DateManager() {
        mContext = Contextor.getInstance().getmContext();
    }
}
