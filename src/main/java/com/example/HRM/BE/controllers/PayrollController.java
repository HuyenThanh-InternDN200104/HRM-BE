package com.example.HRM.BE.controllers;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.DTO.Payroll;
import com.example.HRM.BE.services.DatePayrollDetailService;
import com.example.HRM.BE.services.PayrollsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/payroll_details")
public class PayrollController {
    @Autowired
    PayrollsService payrollsService;

//    @Secured("ROLE_ADMIN")
    @GetMapping("/{month}")
    public List<Payroll> getDatesPayrollByUserID(@PathVariable("month") int month) {
        return this.payrollsService.getPayrollsFollowMonth(month);
    }
}
