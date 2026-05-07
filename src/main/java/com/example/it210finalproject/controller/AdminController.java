package com.example.it210finalproject.controller;

import com.example.it210finalproject.model.dto.BusDTO;
import com.example.it210finalproject.model.dto.EditProfileForm;
import com.example.it210finalproject.model.dto.TripDTO;
import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Route;
import com.example.it210finalproject.model.entity.Trip;
import com.example.it210finalproject.model.entity.User;
import com.example.it210finalproject.service.BusService;
import com.example.it210finalproject.service.RouteService;
import com.example.it210finalproject.service.TripService;
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
    private final TripService tripService;

    public AdminController(RouteService routeService, BusService busService, TripService tripService) {
        this.routeService = routeService;
        this.busService = busService;
        this.tripService = tripService;
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
        Page<Bus> buses = busService.findAll(1, 10);
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
    public String showBusForm(
            Model model,
            @RequestParam(value = "id", required = false) Long id
    ) {
        if (id == null) {
            model.addAttribute("bus", new BusDTO());
        } else {
            Bus bus = busService.findById(id);
            if (bus == null) return "redirect:/admin/add-bus";
            model.addAttribute("bus", bus);
        }
        return "admin/admin-bus-form";
    }

    @PostMapping("/add-bus")
    public String saveBus(
            @Valid @ModelAttribute("bus") BusDTO busDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            @RequestParam(value = "id", required = false) Long id
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/admin-bus-form";
        }

        if (id == null) {
            try {
                busService.addBus(busDTO);
                redirectAttributes.addFlashAttribute("success", "Xe bus đã được thêm thành công");
            } catch (SQLException e) {
                model.addAttribute("addBusError", "Lỗi khi tạo xe vui lòng thử lại");
                return "admin/admin-bus-form";
            }
        } else {
            busService.updateBus(id, busDTO);
        }
        return "redirect:/admin/buses";
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
        Page<Route> routes = routeService.findAll(currentPage, 10);
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

    @GetMapping("/trips")
    public String trips(
            Model model,
            @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage
    ) {
        if (currentPage <= 0) {
            return "redirect:/admin/trips?currentPage=1";
        }
        Page<Trip> trips = tripService.findAll(currentPage, 10);
        if (trips.getTotalPages() > 0 && currentPage > trips.getTotalPages()) {
            return "redirect:/admin/trips?currentPage=" + trips.getTotalPages();
        }
        model.addAttribute("trips", trips.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", trips.getTotalPages());
        return "admin/admin-trips";
    }

    @GetMapping("/add-trip")
    public String showFormTrip(
            Model model,
            @RequestParam(value = "id", required = false) Long id
    ) {
        List<Route> routes = routeService.findAll();
        List<Bus> buses = busService.findAll();
        model.addAttribute("routes", routes);
        model.addAttribute("buses", buses);
        if (id == null) {
            model.addAttribute("trip", new TripDTO());
        } else {
            Trip trip = tripService.findById(id);
            if (trip == null) return "redirect:/admin/add-trip";
            model.addAttribute("trip", new TripDTO(trip.getId(),
                    trip.getRoute().getId(), trip.getBus().getId(),
                    trip.getPrice(), trip.getStartTime()));
        }
        return "admin/admin-trip-form";
    }

    @PostMapping("/add-trip")
    public String saveTrip(
            @Valid @ModelAttribute("trip") TripDTO tripDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "id", required = false) Long id
    ) {
        if (bindingResult.hasErrors()) {
            List<Route> routes = routeService.findAll();
            List<Bus> buses = busService.findAll();
            model.addAttribute("routes", routes);
            model.addAttribute("buses", buses);
            return "admin/admin-trip-form";
        }
        if (id == null) {
            tripService.addTrip(tripDTO);
            redirectAttributes.addFlashAttribute("success", "Thêm chuyến xe thành công");
        } else {
            tripService.updateTrip(id, tripDTO);
            redirectAttributes.addFlashAttribute("success", "Sửa chuyến xe thành công");
        }
        return "redirect:/admin/trips";
    }
}
