package com.example.hms_backend.bloodManagement.repo;


import com.example.hms_backend.bloodManagement.model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface BloodDonorRepository extends JpaRepository<BloodDonor, Long> {

    Optional<BloodDonor> findByDonorId(String donorId);
    Optional<BloodDonor> findByPhone(String phone);
    Optional<BloodDonor> findByEmail(String email);
    List<BloodDonor> findByBloodGroup(String bloodGroup);
    List<BloodDonor> findByIsActiveTrue();
    List<BloodDonor> findByBloodGroupAndIsActiveTrue(String bloodGroup);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(d) FROM BloodDonor d WHERE d.bloodGroup = :bloodGroup AND d.isActive = true")
    Long countActiveDonorsByBloodGroup(@Param("bloodGroup") String bloodGroup);

    @Query("SELECT d FROM BloodDonor d WHERE " +
            "LOWER(d.donorName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "d.phone LIKE CONCAT('%', :searchTerm, '%') OR " +
            "LOWER(d.bloodGroup) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<BloodDonor> searchDonors(@Param("searchTerm") String searchTerm);

    boolean existsByDonorId(String donorId);
}
