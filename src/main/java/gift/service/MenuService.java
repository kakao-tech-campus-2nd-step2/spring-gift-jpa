package gift.service;

import gift.domain.Menu;
import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import gift.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public MenuResponse save(MenuRequest request) {
        Menu menu = Menu.MapMenuRequestToMenu(request);
        return Menu.MapMenuToMenuResponse(menuRepository.save(menu));
    }

    public List<MenuResponse> findall() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(Menu::MapMenuToMenuResponse)
                .collect(Collectors.toList());
    }

    public Menu findById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴 정보가 없습니다."));
        return menu;
    }

    public MenuResponse update(Long id,MenuRequest menuRequest) {
        Menu menu =  menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("메뉴 정보가 없습니다."));
        menu.update(new Menu(id,menuRequest));
        return Menu.MapMenuToMenuResponse(menuRepository.save(menu));
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}
