package com.example.service.offering.controller;

import com.example.service.offering.modal.ServiceOffering;
import com.example.service.offering.service.IServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final IServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(@PathVariable Long salonId, @RequestParam(required = false) Long categoryId) {
        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getAllServiceBySalonId(salonId, categoryId);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServicesById(@PathVariable Long id) throws Exception {
        ServiceOffering serviceOfferings = serviceOfferingService.getServiceById(id);
        return ResponseEntity.ok(serviceOfferings);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(@PathVariable Set ids) {

        Set<ServiceOffering> serviceOfferings = serviceOfferingService.getServiceByIds(ids);
        return ResponseEntity.ok(serviceOfferings);
    }

}
