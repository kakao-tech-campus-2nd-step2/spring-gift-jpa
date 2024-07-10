package gift.mapper;

import gift.domain.MemberRequest;
import gift.domain.Menu;
import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {
    public Menu MapMenuRequestToMenu(MenuRequest menuRequest){
        return new Menu(menuRequest.name(),menuRequest.price(), menuRequest.imageUrl());
    }

    public MenuResponse MapMenuToMenuResponse(Menu menu){
        return new MenuResponse(menu.getId(),menu.getName(),menu.getPrice(),menu.getImageUrl());
    }
}