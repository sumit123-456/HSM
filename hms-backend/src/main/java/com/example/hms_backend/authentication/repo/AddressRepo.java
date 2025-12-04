package com.example.hms_backend.authentication.repo;

import com.example.hms_backend.authentication.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

}
