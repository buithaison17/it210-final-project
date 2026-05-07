package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.dto.BusDTO;
import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Route;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.BusService;
import com.example.it210finalproject.service.RouteService;
import com.example.it210finalproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RouteService routeService;
    private final BusService busService;

    public AdminController(RouteService routeService, BusService busService) {
        this.routeService = routeService;
        this.busService = busService;
    }

    @GetMapping({"", "/dashboard"})
    public String dashboard() {
        return "admin/admin-dashboard";
    }

    @GetMapping("/my-profile")
    public String myProfile(
            Model model,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("editProfileForm", new EditProfileForm(user.getFullName(), user.getEmail(), user.getPhone()));
        return "my-profile";
    }

    @GetMapping("/trips")
    public String trips() {
        return "admin/admin-trips";
    }

    @GetMapping("/tickets")
    public String tickets() {
        return "admin/admin-tickets";
    }

    @GetMapping("/buses")
    public String buses(
            Model model,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        // Nếu số trang âm hoặc bằng 0
        if (currentPage <= 0) {
            return "redirect:/admin/buses?currentPage=1";
        }
        Page<Bus> buses = busService.findAll(1, 2);
        // Nếu trang vượt quá tống trang
        if (buses.getTotalPages() > 0 && currentPage > buses.getTotalPages()) {
            return "redirect:/admin/buses?currentPage=" + buses.getTotalPages();
        }
        // Thêm dữ liệu
        model.addAttribute("buses", buses.getContent());
        model.addAttribute("totalPages", buses.getTotalPages());
        model.addAttribute("currentPage", currentPage);
        return "admin/admin-buses";
    }

    @GetMapping("/add-bus")
    public String showBusForm(Model model) {
        model.addAttribute("bus", new BusDTO());
        return "admin/admin-bus-form";
    }

    @PostMapping("/add-bus")
    public String saveBus(
            @Valid @ModelAttribute("bus") BusDTO busDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/admin-bus-form";
        }
        try {
            busService.addBus(busDTO);
            redirectAttributes.addFlashAttribute("success", "Xe bus đã được thêm thành công");
            return "redirect:/admin/buses";
        } catch (SQLException e) {
            model.addAttribute("addBusError", "Lỗi khi tạo xe vui lòng thử lại");
            return "admin/admin-bus-form";
        }
    }

    @GetMapping("/routes")
    public String routes(
            Model model,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        // Nếu số trang âm hoặc bằng 0
        if (currentPage <= 0) {
            return "redirect:/admin/routes?currentPage=1";
        }
        Page<Route> routes = routeService.findAll(currentPage, 2);
        // Có dữ liệu mới check vượt trang
        if (routes.getTotalPages() > 0
                && currentPage > routes.getTotalPages()) {

            return "redirect:/admin/routes?currentPage="
                    + routes.getTotalPages();
        }
        model.addAttribute("routes", routes);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", routes.getTotalPages());
        return "admin/admin-routes";
    }

    @GetMapping("/add-trip")
    public String showFormTrip(Model model) {
        List<Route> routes = routeService.findAll();
        List<Bus> buses = busService.findAll();
        model.addAttribute("routes", routes);
        model.addAttribute("buses", buses);
        return "admin/admin-trip-form";
    }
}
