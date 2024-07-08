package gift.controller;


import gift.entity.Product;
import gift.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/admin")
    public String mainRendering(){
        return "main";
    }

    @GetMapping("/admin/get")
    public String adminGetPage(Model model) {
        model.addAttribute("products", adminService.getAllProducts());
        return "get";
    }

    @GetMapping("/admin/post")
    public String adminAddPage(){
        return "add";
    }

    @PostMapping("/admin/post/submit")
    public String submitPostProduct(@ModelAttribute @Valid Product product, Model model) {
        adminService.saveProduct(product);
        return adminGetPage(model);
    }

    //DELETE
    @GetMapping("/admin/delete")
    public String adminDeletePage(){
        return "delete";
    }

    @PostMapping("/admin/delete/submit")
    public String submitDeleteProduct(@RequestParam("id") Long id, Model model){
        adminService.deleteProduct(id);
        return adminGetPage(model);
    }

    //Update
    @GetMapping("/admin/put")
    public String adminUpdatePage(){
        return "update";
    }

    @PostMapping("/admin/put/submit")
    public String submitUpdateProduct(@ModelAttribute @Valid Product product, Model model) {
        adminService.updateProduct(product.id(), product);
        return adminGetPage(model);
    }


}
