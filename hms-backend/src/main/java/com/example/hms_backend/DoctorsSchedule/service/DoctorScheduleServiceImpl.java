package com.example.hms_backend.DoctorsSchedule.service;

import com.example.hms_backend.DoctorsSchedule.dto.DoctorScheduleDTO;
import com.example.hms_backend.DoctorsSchedule.entity.DoctorSchedule;
import com.example.hms_backend.DoctorsSchedule.mapper.DoctorScheduleMapper;
import com.example.hms_backend.DoctorsSchedule.repo.DoctorScheduleRepo;
import com.example.hms_backend.department.entity.Department;
import com.example.hms_backend.department.repo.DepartmentRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepo repo;
    private final DepartmentRepo departmentRepo;
    private final DoctorScheduleMapper mapper;
    private final DoctorRepo doctorRepo;

    public DoctorScheduleServiceImpl(DoctorScheduleRepo repo, DepartmentRepo departmentRepo, DoctorScheduleMapper mapper, DoctorRepo doctorRepo) {
        this.repo = repo;
        this.departmentRepo = departmentRepo;
        this.mapper = mapper;
        this.doctorRepo = doctorRepo;
    }

    //method for getting department if it id is not null
    public Department getDepartment(Long doctorId)
    {
        Department department = new Department();
        if (doctorId != null)
        {

            department = departmentRepo.findById(doctorId).orElseThrow(()->new RuntimeException("Department Not Found with id "+doctorId));
        }

        return department;

    }


    //method for getting doctor if it id is not null
    public Doctor getDoctor(Long doctorId)
    {
        Doctor doctor = new Doctor();
        if (doctorId != null)
        {

            doctor = doctorRepo.findById(doctorId).orElseThrow(()->new RuntimeException("Department Not Found with id "+doctorId));
        }

        return doctor;

    }


    @Override
    public DoctorScheduleDTO createSchedule(DoctorScheduleDTO dto) {

        //  Check if schedule already exists
        if (repo.existsByDoctor_Id(dto.getDoctorId())) {
            throw new RuntimeException("Doctor schedule already created");
        }


        DoctorSchedule entity = mapper.toEntity(dto,getDepartment(dto.getDepartmentId()),getDoctor(dto.getDoctorId()));
        DoctorSchedule saved = repo.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public DoctorScheduleDTO updateSchedule(Long id, DoctorScheduleDTO dto) {
        DoctorSchedule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        existing.setDepartment(getDepartment(dto.getDepartmentId()));
        existing.setDoctor(getDoctor(dto.getDoctorId()));
        existing.setAppointmentFees(dto.getAppointmentFees());
        existing.setWeeklySchedule(
                dto.getWeeklySchedule().stream()
                        .map(mapper::toDaySchedule)
                        .collect(Collectors.toList())
        );

        DoctorSchedule updated = repo.save(existing);
        return mapper.toDTO(updated);
    }

    @Override
    public List<DoctorScheduleDTO> getAllSchedules() {
        return repo.findAll().stream()
                .map(schedule -> {
                    DoctorScheduleDTO dto = mapper.toDTO(schedule);

                    // Add business logic here (NOT in mapper)
                    dto.setStatus(getStatusById(schedule.getDoctor().getId()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public DoctorScheduleDTO getScheduleById(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    @Override
    public void deleteSchedule(Long id) {
        repo.deleteById(id);
    }

    @Override
    public String getStatusById(Long doctorId) {
       Doctor doc= doctorRepo.findById(doctorId).orElseThrow(()->new RuntimeException("Doctor not found with Id"+doctorId));
       return doc.getUserEntity().getUserInfo().getAvailabilityStatus().toString();
    }
}
