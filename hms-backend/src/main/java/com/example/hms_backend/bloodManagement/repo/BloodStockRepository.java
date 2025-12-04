package com.example.hms_backend.bloodManagement.repo;


import com.example.hms_backend.bloodManagement.model.BloodStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BloodStockRepository extends JpaRepository<BloodStock,Long> {
    Optional<BloodStock> findByStockId(String stockId);
    List<BloodStock> findByBloodGroup(String bloodGroup);
    List<BloodStock> findByIsActiveTrue();
    List<BloodStock> findByBloodGroupAndIsActiveTrue(String bloodGroup);
    List<BloodStock> findByExpiryDateBeforeAndIsActiveTrue(LocalDate date);

    @Query("SELECT SUM(bs.unitsAvailable) FROM BloodStock bs WHERE bs.bloodGroup = :bloodGroup AND bs.isActive = true")
    Integer getTotalUnitsByBloodGroup(@Param("bloodGroup") String bloodGroup);

    boolean existsByStockId(String stockId);

    Optional<BloodStock> findByDonationDonationId(String donationId);
}
