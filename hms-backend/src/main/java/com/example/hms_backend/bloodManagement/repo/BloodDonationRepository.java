package com.example.hms_backend.bloodManagement.repo;


import com.example.hms_backend.bloodManagement.model.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {

    Optional<BloodDonation> findByDonationId(String donationId);

    // ✅ FIXED: Use correct field name - assuming it's 'donor' relationship
    @Query("SELECT d FROM BloodDonation d WHERE d.donor.donorId = :donorId")
    List<BloodDonation> findByDonorDonorId(@Param("donorId") String donorId);

    List<BloodDonation> findByBloodGroup(String bloodGroup);

    List<BloodDonation> findByStatus(String status);

    List<BloodDonation> findByExpiryDateBefore(LocalDate date);

    List<BloodDonation> findByDonationDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(d) FROM BloodDonation d WHERE d.donor.donorId = :donorId")
    Long countByDonorId(@Param("donorId") String donorId);

    @Query("SELECT d FROM BloodDonation d WHERE d.donor.donorId = :donorId ORDER BY d.donationDate DESC")
    List<BloodDonation> findDonorHistory(@Param("donorId") String donorId);

    @Query("SELECT d FROM BloodDonation d WHERE d.status = 'AVAILABLE' AND d.id NOT IN (SELECT s.donation.id FROM BloodStock s WHERE s.donation IS NOT NULL)")
    List<BloodDonation> findAvailableDonationsForStock();

    @Query("SELECT d FROM BloodDonation d WHERE d.status = 'AVAILABLE' AND d NOT IN (SELECT s.donation FROM BloodStock s)")
    List<BloodDonation> findAvailableDonations();
}
