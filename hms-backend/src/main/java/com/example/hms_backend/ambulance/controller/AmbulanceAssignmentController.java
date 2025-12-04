//package com.example.hms_backend.ambulance.controller;
//
//import com.example.hms_backend.ambulance.dto.AmbulanceAssignmentDTO;
//import com.example.hms_backend.ambulance.service.AmbulanceAssignmentService;
//import com.example.hms_backend.ambulance.service.AmbulanceService;
//import com.example.hms_backend.ambulance.service.DriverService;
//import com.example.hms_backend.enums.AmbulanceEnums;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class AmbulanceAssignmentController {
//
//    @Autowired
//    private AmbulanceAssignmentService ambulanceAssignmentService;
//
//    @Autowired
//    AmbulanceService ambulanceService;
//
//    @Autowired
//    private DriverService driverService;
//
//
//    @GetMapping("/ambulance/assignment")
//    @PreAuthorize("hasAuthority('AMBULANCE_ASSIGNMENT')")
//    public String showAmbulanceAssignmentFragment(Model model) {
//        model.addAttribute("driverList",driverService.findAllDriver());
//        model.addAttribute("ambulanceList",ambulanceService.findAllAmbulance());
//        model.addAttribute("assignmentStatus", AmbulanceEnums.AssignmentStatus.values());
//        model.addAttribute("ambulanceAssignmentDto",new AmbulanceAssignmentDTO());
//        return "ambulance/ambulance-assignment :: ambulanceAssignmentFragment";
//    }
//
//
//
//    @PostMapping("/ambulance/assignment/add")
//    @PreAuthorize("hasAuthority('AMBULANCE_ASSIGNMENT')")
//    public  String addAmbulanceAssignment(@Valid @ModelAttribute("ambulanceAssignmentDto")
//                                              AmbulanceAssignmentDTO ambulanceAssignmentDTO,
//                                          BindingResult result,
//                                          RedirectAttributes redirectAttributes,
//                                          Model model)
//
//    {
//
//
//        if (result.hasErrors()) {
//            System.out.println(result.getAllErrors().toString());
//            model.addAttribute("driverList",driverService.findAllDriver());
//            model.addAttribute("assignmentStatus", AmbulanceEnums.AssignmentStatus.values());
//            model.addAttribute("ambulanceList",ambulanceService.findAllAmbulance());
//            return "ambulance/ambulance-assignment :: ambulanceAssignmentFragment";
//
//        }
//
//        try {
//            ambulanceAssignmentService.saveAmbulanceAssignment(ambulanceAssignmentDTO);
//            model.addAttribute("successMsg", "Ambulance Trip Assign Successfully!");
//
//            System.out.println("ambulance assignment controller");
//
//        }
//        catch (DataIntegrityViolationException e) {
//            model.addAttribute("driverList",driverService.findAllDriver());
//            model.addAttribute("ambulanceList",ambulanceService.findAllAmbulance());
//            model.addAttribute("assignmentStatus", AmbulanceEnums.AssignmentStatus.values());
//            model.addAttribute("error","Please enter Valid data");
//            return "ambulance/ambulance-assignment :: ambulanceAssignmentFragment";
//        }
//
//
//        model.addAttribute("ambulances", ambulanceService.findAllAmbulanceFullObject());
//        model.addAttribute("drivers", driverService.findAllDriverFullObject());
//        model.addAttribute("assignments", ambulanceAssignmentService.findAllInProgressAmbulanceAssignments());
//        model.addAttribute("completedAssignments", ambulanceAssignmentService.findAllCompletedAmbulanceAssignments());
//        return "ambulance/ambulance-view :: ambulance-view";
//    }
//
//
//    @PostMapping("/assignments/changeStatus/{id}")
//    public String changeAssignmentStatus(@PathVariable Long id,
//                                         @RequestParam String status,
//                                         Model model,
//                                         RedirectAttributes redirectAttributes)
//    {
//        ambulanceAssignmentService.updateAssignmentStatus(id, status);
//        model.addAttribute("successMsg", "Assignment Status Changed Successfully!");
//
//        // Reload updated lists for the fragment
//        model.addAttribute("ambulances", ambulanceService.findAllAmbulanceFullObject());
//        model.addAttribute("drivers", driverService.findAllDriverFullObject());
//        model.addAttribute("assignments", ambulanceAssignmentService.findAllInProgressAmbulanceAssignments());
//        model.addAttribute("completedAssignments", ambulanceAssignmentService.findAllCompletedAmbulanceAssignments());
//
//        return "ambulance/ambulance-view :: ambulance-view";
//
//    }
//
//}












package com.example.hms_backend.ambulance.controller;

import com.example.hms_backend.ambulance.dto.AmbulanceAssignmentDTO;
import com.example.hms_backend.ambulance.service.AmbulanceAssignmentService;
import com.example.hms_backend.ambulance.service.AmbulanceService;
import com.example.hms_backend.ambulance.service.DriverService;
import com.example.hms_backend.enums.AmbulanceEnums;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ambulance/assignment")
@CrossOrigin(origins = "*")
public class AmbulanceAssignmentController {

    @Autowired
    private AmbulanceAssignmentService ambulanceAssignmentService;

    @Autowired
    private AmbulanceService ambulanceService;

    @Autowired
    private DriverService driverService;


    // ✅ Get needed data for front-end form dropdowns
    @GetMapping("/form-data")
//    @PreAuthorize("hasAuthority('AMBULANCE_ASSIGNMENT')")
    public ResponseEntity<Map<String, Object>> getFormData() {

        Map<String, Object> data = new HashMap<>();
        data.put("driverList", driverService.findAllDriver());
        data.put("ambulanceList", ambulanceService.findAllAmbulance());
        data.put("assignmentStatus", AmbulanceEnums.AssignmentStatus.values());

        return ResponseEntity.ok(data);
    }



    // ✅ Create new assignment
    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('AMBULANCE_ASSIGNMENT')")
    public ResponseEntity<?> addAmbulanceAssignment(@Valid @RequestBody AmbulanceAssignmentDTO dto) {

        try {
            ambulanceAssignmentService.saveAmbulanceAssignment(dto);

            return ResponseEntity.ok(
                    Map.of("message", "Ambulance Trip Assigned Successfully!")
            );
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Invalid data, duplicate entry or constraint violation")
            );
        }
    }



    // ✅ Update Assignment Status
    @PutMapping("/status/{id}")
//    @PreAuthorize("hasAuthority('AMBULANCE_ASSIGNMENT')")
    public ResponseEntity<?> changeAssignmentStatus(@PathVariable Long id,
                                                    @RequestParam String status) {

        ambulanceAssignmentService.updateAssignmentStatus(id, status);

        return ResponseEntity.ok(
                Map.of("message", "Assignment Status Updated Successfully!")
        );
    }


    // ✅ View All Lists (for dashboard)
    @GetMapping("/view")
//    @PreAuthorize("hasAuthority('AMBULANCE_ASSIGNMENT')")
    public ResponseEntity<Map<String, Object>> viewAssignments() {

        Map<String, Object> data = new HashMap<>();
        data.put("ambulances", ambulanceService.findAllAmbulanceFullObject());
        data.put("drivers", driverService.findAllDriverFullObject());
        data.put("assignments", ambulanceAssignmentService.findAllInProgressAmbulanceAssignments());
        data.put("completedAssignments", ambulanceAssignmentService.findAllCompletedAmbulanceAssignments());

        return ResponseEntity.ok(data);
    }

}
