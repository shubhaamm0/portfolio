package com.portfolio.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = 500;
        if (status != null) {
            try {
                statusCode = Integer.parseInt(status.toString());
            } catch (NumberFormatException ignored) {
                statusCode = 500;
            }
        }

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute("statusCode", 404);
            model.addAttribute("errorTitle", "Page Not Found");
            model.addAttribute("errorMessage", "The page or resource you requested does not exist or has been moved.");
            return "error/404";
        }

        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute("errorMessage", "An unexpected error occurred while processing your request.");
        return "error/500";
    }
}
