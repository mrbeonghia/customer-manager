package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public ModelAndView listCustomer(){
        ModelAndView modelAndView = new ModelAndView("index");
        List customerList = customerService.findAll();
        modelAndView.addObject("customers",customerList);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView save(@ModelAttribute Customer customer){
        int id = customerService.findAll().size();
        customer.setId(id);
        customerService.update(id,customer);
        ModelAndView modelAndView= new ModelAndView("create");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, ModelMap modelMap){
        Customer customer = customerService.findById(id)    ;
        modelMap.addAttribute("customer", customer);
        return "edit";
    }
    @PostMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable int id, @ModelAttribute Customer customer){
        customer.setId(id);
        customerService.update(id,customer);
        ModelAndView modelAndView = new ModelAndView("redirect:/customers");
        modelAndView.addObject("customer", customerService.findById(id));
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "delete";
    }
    @PostMapping("delete")
    public String delete(Customer customer, RedirectAttributes redirect) {
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("success", "Removed customer successfully!");
        return "redirect:/customers";
    }

    @GetMapping("view/{id}")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "view";
    }

}
