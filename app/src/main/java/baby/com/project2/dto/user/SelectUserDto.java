package baby.com.project2.dto.user;

import java.util.List;

import baby.com.project2.dto.child.SelectChildItemsDto;

public class SelectUserDto {

    private List<SelectUserItemsDto> User;

    public List<SelectUserItemsDto> getUser() {
        return User;
    }

    public void setUser(List<SelectUserItemsDto> user) {
        User = user;
    }
}
