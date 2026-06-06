package kr.ac.hansung.controller;

import kr.ac.hansung.entity.Product;
import kr.ac.hansung.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));

        String normalizedKeyword = (keyword != null && !keyword.isBlank()) ? keyword : null;

        Page<Product> productPage;
        if (normalizedKeyword != null) {
            productPage = productService.searchProducts(normalizedKeyword, pageRequest);
        } else {
            productPage = productService.getProducts(pageRequest);
        }

        model.addAttribute("products", productPage);
        model.addAttribute("page", productPage);
        model.addAttribute("productPage", productPage);

        model.addAttribute("keyword", keyword);
        model.addAttribute("searchKeyword", keyword);

        return "products/list";
    }
}