package gift.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import gift.model.item.ItemDTO;
import gift.model.item.ItemForm;
import gift.service.ItemService;

@Controller
@RequestMapping("/product")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public String getItemList(Model model) {
        model.addAttribute("list", itemService.getList());
        return "list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model, ItemForm form) {
        model.addAttribute("item", form);
        return "create";
    }

    @PostMapping("/create")
    public String createItem(@Valid @ModelAttribute("item") ItemForm form, BindingResult result,
        HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "create";
        }
        itemService.insertItem(form);
        return "redirect:/product/list";
    }

    @GetMapping("/update/{id}")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        ItemDTO itemDTO = itemService.findItem(id);
        ItemForm form = new ItemForm(itemDTO.getName(), itemDTO.getPrice(), itemDTO.getImgUrl());
        model.addAttribute("item", form);
        model.addAttribute("id", id);
        return "update";
    }

    @PutMapping("/update/{id}")
    public String updateItem(@PathVariable Long id, @Valid @ModelAttribute("item") ItemForm form,
        BindingResult result,
        HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "update";
        }
        ItemDTO itemDTO = new ItemDTO(id, form.getName(), form.getPrice(), form.getImgUrl());
        itemService.updateItem(itemDTO);
        return "redirect:/product/list";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/product/list";
    }
}