package gift.service;

import gift.controller.MenuController;
import gift.domain.Menu;
import gift.domain.MenuRequest;
import gift.domain.MenuResponse;
import gift.repository.MenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Menu menu = MenuController.MapMenuRequestToMenu(request);
        return MenuController.MapMenuToMenuResponse(menuRepository.save(menu));
    }

    public List<MenuResponse> findall(
            Pageable pageable
    ) {
        Page<Menu> menus = menuRepository.findAll(pageable);
        return menus.stream()
                .map(MenuController::MapMenuToMenuResponse)
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
        return MenuController.MapMenuToMenuResponse(menuRepository.save(menu));
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}
