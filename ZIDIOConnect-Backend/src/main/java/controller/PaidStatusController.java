package com.zidioconnect.controller;

import com.zidioconnect.model.PaidStatus;
import com.zidioconnect.service.PaidStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paid-status")
@CrossOrigin(origins = "*")
public class PaidStatusController {

    @Autowired
    private PaidStatusService paidStatusService;

    @PostMapping("/set")
    public PaidStatus setPaidStatus(@RequestBody PaidStatus paidStatus) {
        return paidStatusService.setPaidStatus(paidStatus);
    }

    @GetMapping("/student/{studentId}")
    public PaidStatus getPaidStatusByStudentId(@PathVariable Long studentId) {
        return paidStatusService.getPaidStatusByStudentId(studentId);
    }

    @GetMapping("/all")
    public List<PaidStatus> getAllPaidStatuses() {
        return paidStatusService.getAllPaidStatuses();
    }
}
