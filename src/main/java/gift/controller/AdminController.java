package gift.controller;


import gift.model.GiftRequest;
import gift.model.GiftResponse;
import gift.service.GiftService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class AdminController {

    private final GiftService giftService;

    public AdminController(GiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping
    public String index() {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminHome(Model model) {
        Collection<GiftResponse> giftlist = giftService.getAllGifts(null);
        model.addAttribute("giftlist", giftlist);
        return "admin";
    }

    @GetMapping("/admin/gift/create")
    public String giftCreate() {
        return "create_form";
    }

    @PostMapping("/admin/gift/create")
    public String giftCreate(@Valid @ModelAttribute GiftRequest giftRequest) {
        giftService.addGift(giftRequest);
        return "redirect:/admin";
    }

    @GetMapping("/admin/gift/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        GiftResponse gift = giftService.getGift(id);
        model.addAttribute("gift", gift);
        return "gift_detail";
    }

    @GetMapping("/admin/gift/modify/{id}")
    public String giftModify(Model model, @PathVariable("id") Long id) {
        GiftResponse gift = giftService.getGift(id);
        model.addAttribute("gift", gift);
        return "modify_form";
    }

    @PutMapping("/admin/gift/modify/{id}")
    public String giftModify(@PathVariable("id") Long id, @ModelAttribute GiftRequest giftRequest) {
        giftService.updateGift(giftRequest, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/gift/delete/{id}")
    public String giftDelete(@PathVariable("id") Long id) {
        giftService.deleteGift(id);
        return "redirect:/admin";
    }
}
