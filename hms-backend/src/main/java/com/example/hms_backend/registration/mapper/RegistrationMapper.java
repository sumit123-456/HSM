package com.example.hms_backend.registration.mapper;

import com.example.hms_backend.authentication.dto.AddressDto;
import com.example.hms_backend.authentication.entity.Address;
import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.entity.UserInfo;
import com.example.hms_backend.enums.Enums;
import com.example.hms_backend.registration.dto.RegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class RegistrationMapper {
    private final PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(RegistrationDto dto, Role role) {

        System.out.println("inside toUserEntity");

        if (role == null) {
            System.out.println("Role is null");
        } else {
            System.out.println("Role ID: " + role.getRoleId()); // or role.getRoleName()
        }



        UserEntity user = new UserEntity();
        System.out.println("x");

        user.setUsername(dto.getUsername());
        System.out.println("y");

        user.setEmail(dto.getEmail());
        System.out.println("z");

        user.setPassword(passwordEncoder.encode(dto.getConfirmPassword()));
        System.out.println("a");

        user.setEnabled(true);
        System.out.println("b");

//        user.setRoles(new HashSet<>(Set.of(role)));
        Set<Role> roles = new HashSet<>();
        System.out.println("c");
        roles.add(role);
        System.out.println("d");
        user.setRoles(roles);

        System.out.println("e");
        System.out.println("Roles set on user: " + user.getRoles());
        return user;
    }

    public Address toAddress(AddressDto dto) {
        Address address = new Address();
        address.setId(dto.getId()); // optional if updating
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPincode(dto.getPincode());
        return address;
    }



    public UserInfo toUserInfo(RegistrationDto dto, UserEntity user, Address address) throws IOException {
        System.out.println("inside toUserInfo");
        UserInfo info = new UserInfo();
        // info.setUserEntity(user);
        info.setUserEntity(user);
        info.setAddress(address);
        info.setFirstName(dto.getFirstName());
        info.setLastName(dto.getLastName());
        info.setMobileNumber(dto.getMobileNumber());
//        info.setProfilePic(dto.getProfilePic().getBytes());
        if (dto.getProfilePic() != null && !dto.getProfilePic().isEmpty()) {
            info.setProfilePic(dto.getProfilePic().getBytes());
        } else {
            info.setProfilePic(null); // or set default blank image
        }
        info.setGender(dto.getGender());
        info.setDob(dto.getDob());
        info.setAvailabilityStatus(Enums.AvailabilityStatus.ACTIVE);
        info.setAge(Period.between(dto.getDob(), LocalDate.now()).getYears());
        info.setJoiningDate(dto.getJoiningDate());
        info.setBloodGroup(dto.getBloodGroup());
        info.setIdProofName(dto.getIdProofType().toString());
        if (dto.getIdProofPic() != null && !dto.getIdProofPic().isEmpty()) {
            info.setIdProofPic(dto.getIdProofPic().getBytes());
        } else {
            info.setIdProofPic(null);
        }
        return info;
    }


}
