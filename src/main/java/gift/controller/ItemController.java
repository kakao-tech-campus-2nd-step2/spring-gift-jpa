package gift.controller;

import gift.model.item.ItemDTO;
import gift.model.item.ItemForm;
import gift.service.ItemService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public List<ItemDTO> getItemList(Model model) {
        return itemService.getList();
    }

    @PostMapping("/")
    public HttpStatus createItem(@Valid @RequestBody ItemForm form, BindingResult result,
        HttpServletResponse response) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result);
        }
        itemService.insertItem(form);
        return HttpStatus.CREATED;
    }


    @PutMapping("/{id}")
    public HttpStatus updateItem(@PathVariable Long id, @Valid @RequestBody ItemForm form,
        BindingResult result) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result);
        }
        ItemDTO itemDTO = new ItemDTO(id, form.getName(), form.getPrice(), form.getImgUrl());
        itemService.updateItem(itemDTO);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return HttpStatus.OK;
    }
}