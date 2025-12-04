package com.example.hms_backend.hr.service;

import com.example.hms_backend.accountant.entity.Accountant;
import com.example.hms_backend.accountant.repository.AccountantRepo;
import com.example.hms_backend.authentication.entity.Address;
import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.entity.UserInfo;
import com.example.hms_backend.authentication.repo.RoleRepo;
import com.example.hms_backend.authentication.repo.UserRepo;
import com.example.hms_backend.doctor.entity.Doctor;
import com.example.hms_backend.doctor.repo.DoctorRepo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.head_nurse.entity.HeadNurse;
import com.example.hms_backend.head_nurse.repo.HeadNurseRepository;
import com.example.hms_backend.hr.dto.EmployeeUpdateDTO;
import com.example.hms_backend.hr.dto.EmployeeResponseDTO;
import com.example.hms_backend.hr.entity.HumanResource;
import com.example.hms_backend.hr.repo.HumanResourceRepo;
import com.example.hms_backend.insurance.entity.Insurer;
import com.example.hms_backend.insurance.repo.InsurerRepo;
import com.example.hms_backend.laboratory.laboratorist.entity.Laboratorist;
import com.example.hms_backend.laboratory.laboratorist.repo.LaboratoristRepo;
import com.example.hms_backend.pharmacy.pharmacist.entity.Pharmacist;
import com.example.hms_backend.pharmacy.pharmacist.repo.PharmacistRepo;
import com.example.hms_backend.receptionist.entity.Receptionist;
import com.example.hms_backend.receptionist.repo.ReceptionistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManageEmployeeServiceImpl implements ManageEmployeeService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    HumanResourceRepo humanResourceRepo;
    @Autowired
    private LaboratoristRepo laboratoristRepo;
    @Autowired
    private AccountantRepo accountantRepo;
    @Autowired
    private HeadNurseRepository headNurseRepository;
    @Autowired
    private InsurerRepo insurerRepo;
    @Autowired
    private PharmacistRepo pharmacistRepo;
    @Autowired
    private ReceptionistRepo receptionistRepo;




@Override
public List<EmployeeResponseDTO> getUsersByRole(Long roleId) {

    String roleName = roleRepo.findById(roleId)
            .map(Role::getRoleName)
            .orElse(null);

    List<UserEntity> users = userRepo.findByRoles_RoleId(roleId);

    return users.stream().map(user -> {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        // ===== Common User fields =====
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        // Roles
        dto.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getRoleName)
                        .collect(Collectors.toList())
        );

        // ===== UserInfo fields =====
        if (user.getUserInfo() != null) {
            dto.setFirstName(user.getUserInfo().getFirstName());
            dto.setLastName(user.getUserInfo().getLastName());
            dto.setBloodGroup(user.getUserInfo().getBloodGroup().toString());
            dto.setJoiningDate(user.getUserInfo().getJoiningDate().toString());
            dto.setGender(user.getUserInfo().getGender().toString());
            dto.setMobileNo(user.getUserInfo().getMobileNumber());
            dto.setAge(user.getUserInfo().getAge());
            dto.setDob(user.getUserInfo().getDob().toString());

            // Profile image
            if (user.getUserInfo().getProfilePic() != null) {
                dto.setProfilePic(
                        "data:image/jpeg;base64," +
                                Base64.getEncoder().encodeToString(user.getUserInfo().getProfilePic())
                );
            }

            // ID proof
            if (user.getUserInfo().getIdProofPic() != null) {
                dto.setIdProofPic(
                        "data:image/jpeg;base64," +
                                Base64.getEncoder().encodeToString(user.getUserInfo().getIdProofPic())
                );
            }

            // Address
            if (user.getUserInfo().getAddress() != null) {
                dto.setAddress1(user.getUserInfo().getAddress().getAddressLine1());
                dto.setAddress2(user.getUserInfo().getAddress().getAddressLine2());
                dto.setDistrict(user.getUserInfo().getAddress().getDistrict());
                dto.setCity(user.getUserInfo().getAddress().getCity());
                dto.setCountry(user.getUserInfo().getAddress().getCountry());
                dto.setStatus(user.getUserInfo().getAvailabilityStatus().toString());
            }
        }

        // ===== Role-Specific Fields =====

        switch (roleName) {

            case "ROLE_DOCTOR" -> {
                Doctor doctor = doctorRepo.findByUserEntity(user);
                if (doctor != null) {
                    dto.setSpecialization(doctor.getSpecialization());
                    dto.setLicenseNo(doctor.getLicenseNumber());
                    dto.setExperience(doctor.getExperience());
                    dto.setQualification(doctor.getQualifications());
                }
            }

            case "ROLE_LABORATORIST" -> {
                Laboratorist lb = laboratoristRepo.findByUserEntity(user);
                if (lb != null) {
                    dto.setExperience(lb.getExperience());
                    dto.setQualification(lb.getQualifications());
                    dto.setCategory(lb.getLaboratoryType().toString());
                }
            }

            case "ROLE_HR" -> {
                HumanResource hr = humanResourceRepo.findByUserEntity(user);
                if (hr != null) {
                    dto.setExperience(hr.getExperience());
                    dto.setQualification(hr.getQualifications());
                }
            }

            case "ROLE_ACCOUNTANT" -> {
                Accountant ac = accountantRepo.findByUserEntity(user);
                if (ac != null) {
                    dto.setExperience(ac.getExperience());
                    dto.setQualification(ac.getQualifications());
                }
            }

            case "ROLE_HEADNURSE" -> {
                HeadNurse hn = headNurseRepository.findByUserEntity(user);
                if (hn != null) {
                    dto.setExperience(hn.getExperience());
                    dto.setQualification(hn.getQualifications());
                }
            }

            case "ROLE_INSURANCE" -> {
                Insurer ins = insurerRepo.findByUserEntity(user);
                if (ins != null) {
                    dto.setExperience(ins.getExperience());
                    dto.setQualification(ins.getQualifications());
                }
            }

            case "ROLE_PHARMACIST" -> {
                Pharmacist ph = pharmacistRepo.findByUserEntity(user);
                if (ph != null) {
                    dto.setExperience(ph.getExperience());
                    dto.setQualification(ph.getQualifications());
                }
            }

            case "ROLE_RECEPTIONIST" -> {
                Receptionist rec = receptionistRepo.findByUserEntity(user);
                if (rec != null) {
                    dto.setExperience(rec.getExperience());
                    dto.setQualification(rec.getQualifications());
                }
            }
        }

        return dto;

    }).collect(Collectors.toList());
}

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long userId, EmployeeUpdateDTO dto, MultipartFile idProofPic) {

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));



        // ================= USER INFO =================
        UserInfo info = user.getUserInfo();
        if (info == null) {
            info = new UserInfo();
            info.setUserEntity(user);
            user.setUserInfo(info);
        }

        if (dto.getFirstName() != null) info.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) info.setLastName(dto.getLastName());

        if (dto.getGender() != null) info.setGender(Enums.Gender.valueOf(dto.getGender()));
        if (dto.getBloodGroup() != null) info.setBloodGroup(Enums.BloodGroup.valueOf(dto.getBloodGroup()));
        if (dto.getJoiningDate() != null) info.setJoiningDate(LocalDate.parse(dto.getJoiningDate()));
        if (dto.getDob() != null) info.setDob(LocalDate.parse(dto.getDob()));

        if (dto.getAge() != null) info.setAge(dto.getAge());
        if (dto.getMobileNo() != null) info.setMobileNumber(dto.getMobileNo());



        // ================= IDPROOF IMAGE UPDATE =================

        if (idProofPic!= null) {

            try {
                info.setIdProofPic(idProofPic.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // ================= ADDRESS UPDATE =================
        if (info.getAddress() == null) info.setAddress(new Address());

        Address address = info.getAddress();
        if (dto.getAddress1() != null) address.setAddressLine1(dto.getAddress1());
        if (dto.getAddress2() != null) address.setAddressLine2(dto.getAddress2());
        if (dto.getDistrict() != null) address.setDistrict(dto.getDistrict());
        if (dto.getCity() != null) address.setCity(dto.getCity());
        if (dto.getCountry() != null) address.setCountry(dto.getCountry());

        // ================= ROLE-SPECIFIC FIELDS =================
        String roleName = user.getRoles().stream()
                .map(Role::getRoleName)
                .findFirst()
                .orElse(null);

        switch (roleName) {

            case "ROLE_DOCTOR" -> {
                Doctor doc = doctorRepo.findByUserEntity(user);
                if (doc != null) {
                    if (dto.getSpecialization() != null) doc.setSpecialization(dto.getSpecialization());
                    if (dto.getLicenseNo() != null) doc.setLicenseNumber(dto.getLicenseNo());
                    if (dto.getExperience() != null) doc.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) doc.setQualifications(dto.getQualification());
                    doctorRepo.save(doc);
                }
            }

            case "ROLE_LABORATORIST" -> {
                Laboratorist lb = laboratoristRepo.findByUserEntity(user);
                if (lb != null) {
                    if (dto.getExperience() != null) lb.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) lb.setQualifications(dto.getQualification());
                    if (dto.getCategory() != null) lb.setLaboratoryType(Enums.LaboratoryType.valueOf(dto.getCategory()));
                    laboratoristRepo.save(lb);
                }
            }

            case "ROLE_HR" -> {
                HumanResource hr = humanResourceRepo.findByUserEntity(user);
                if (hr != null) {
                    if (dto.getExperience() != null) hr.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) hr.setQualifications(dto.getQualification());
                    humanResourceRepo.save(hr);
                }
            }

            case "ROLE_ACCOUNTANT" -> {
                Accountant ac = accountantRepo.findByUserEntity(user);
                if (ac != null) {
                    if (dto.getExperience() != null) ac.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) ac.setQualifications(dto.getQualification());
                    accountantRepo.save(ac);
                }
            }

            case "ROLE_HEADNURSE" -> {
                HeadNurse hn = headNurseRepository.findByUserEntity(user);
                if (hn != null) {
                    if (dto.getExperience() != null) hn.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) hn.setQualifications(dto.getQualification());
                    headNurseRepository.save(hn);
                }
            }

            case "ROLE_INSURANCE" -> {
                Insurer ins = insurerRepo.findByUserEntity(user);
                if (ins != null) {
                    if (dto.getExperience() != null) ins.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) ins.setQualifications(dto.getQualification());
                    insurerRepo.save(ins);
                }
            }

            case "ROLE_PHARMACIST" -> {
                Pharmacist ph = pharmacistRepo.findByUserEntity(user);
                if (ph != null) {
                    if (dto.getExperience() != null) ph.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) ph.setQualifications(dto.getQualification());
                    pharmacistRepo.save(ph);
                }
            }

            case "ROLE_RECEPTIONist" -> {
                Receptionist rec = receptionistRepo.findByUserEntity(user);
                if (rec != null) {
                    if (dto.getExperience() != null) rec.setExperience(dto.getExperience());
                    if (dto.getQualification() != null) rec.setQualifications(dto.getQualification());
                    receptionistRepo.save(rec);
                }
            }
        }

        // Save all updates
        userRepo.save(user);

        // Return updated response DTO (You already have mapper)
        return convertToDto(user);
    }



    @Override
    public String updateStatus(Long userId, String status) {
        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.getUserInfo().setAvailabilityStatus(Enums.AvailabilityStatus.valueOf(status));
        userRepo.save(user);


        return "Status Update SuccessFully!";
    }




    //conver to DTO
    private EmployeeResponseDTO convertToDto(UserEntity user) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        // ================= USER INFO =================
        if (user.getUserInfo() != null) {

            UserInfo info = user.getUserInfo();

            dto.setFirstName(info.getFirstName());
            dto.setLastName(info.getLastName());

            if (info.getBloodGroup() != null)
                dto.setBloodGroup(info.getBloodGroup().toString());

            if (info.getJoiningDate() != null)
                dto.setJoiningDate(info.getJoiningDate().toString());

            if (info.getGender() != null)
                dto.setGender(info.getGender().toString());

            dto.setMobileNo(info.getMobileNumber());
            dto.setAge(info.getAge());

            if (info.getDob() != null)
                dto.setDob(info.getDob().toString());

            // ========== Images ==========


            if (info.getIdProofPic() != null) {
                dto.setIdProofPic("data:image/jpeg;base64," +
                        Base64.getEncoder().encodeToString(info.getIdProofPic()));
            }

            // ========== Address ==========
            if (info.getAddress() != null) {

                Address ad = info.getAddress();

                dto.setAddress1(ad.getAddressLine1());
                dto.setAddress2(ad.getAddressLine2());
                dto.setDistrict(ad.getDistrict());
                dto.setCity(ad.getCity());
                dto.setCountry(ad.getCountry());
            }

            if (info.getAvailabilityStatus() != null)
                dto.setStatus(info.getAvailabilityStatus().toString());
        }

        // ================= ROLE-SPECIFIC DETAILS =================
        List<String> roles = user.getRoles()
                .stream().map(Role::getRoleName).toList();

        // Doctor
        if (roles.contains("ROLE_DOCTOR")) {
            Doctor d = doctorRepo.findByUserEntity(user);
            if (d != null) {
                dto.setSpecialization(d.getSpecialization());
                dto.setLicenseNo(d.getLicenseNumber());
                dto.setExperience(d.getExperience());
                dto.setQualification(d.getQualifications());
            }
        }

        // Laboratorist
        if (roles.contains("ROLE_LABORATORIST")) {
            Laboratorist lb = laboratoristRepo.findByUserEntity(user);
            if (lb != null) {
                dto.setCategory(lb.getLaboratoryType().toString());
                dto.setExperience(lb.getExperience());
                dto.setQualification(lb.getQualifications());
            }
        }

        // HR
        if (roles.contains("ROLE_HR")) {
            HumanResource hr = humanResourceRepo.findByUserEntity(user);
            if (hr != null) {
                dto.setExperience(hr.getExperience());
                dto.setQualification(hr.getQualifications());
            }
        }

        // Accountant
        if (roles.contains("ROLE_ACCOUNTANT")) {
            Accountant ac = accountantRepo.findByUserEntity(user);
            if (ac != null) {
                dto.setExperience(ac.getExperience());
                dto.setQualification(ac.getQualifications());
            }
        }

        // Head Nurse
        if (roles.contains("ROLE_HEADNURSE")) {
            HeadNurse hn = headNurseRepository.findByUserEntity(user);
            if (hn != null) {
                dto.setExperience(hn.getExperience());
                dto.setQualification(hn.getQualifications());
            }
        }

        // Insurance Officer
        if (roles.contains("ROLE_INSURANCE")) {
            Insurer ins = insurerRepo.findByUserEntity(user);
            if (ins != null) {
                dto.setExperience(ins.getExperience());
                dto.setQualification(ins.getQualifications());
            }
        }

        // Pharmacist
        if (roles.contains("ROLE_PHARMACIST")) {
            Pharmacist ph = pharmacistRepo.findByUserEntity(user);
            if (ph != null) {
                dto.setExperience(ph.getExperience());
                dto.setQualification(ph.getQualifications());
            }
        }

        // Receptionist
        if (roles.contains("ROLE_RECEPTIONIST")) {
            Receptionist rc = receptionistRepo.findByUserEntity(user);
            if (rc != null) {
                dto.setExperience(rc.getExperience());
                dto.setQualification(rc.getQualifications());
            }
        }

        return dto;
    }
}
