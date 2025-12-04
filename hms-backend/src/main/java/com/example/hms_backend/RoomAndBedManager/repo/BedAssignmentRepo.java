package com.example.hms_backend.RoomAndBedManager.repo;

import com.example.hms_backend.RoomAndBedManager.entity.BedAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BedAssignmentRepo extends JpaRepository<BedAssignment, Long>
{

    // Fetch single active assignment
    BedAssignment findByPatientId(Long patientId);

    // OR if patient may have multiple assignments in history
    List<BedAssignment> findAllByPatientId(Long patientId);

    //jpql for checking currently assign bed
    @Query("SELECT ba FROM BedAssignment ba WHERE ba.releasedAt IS NULL")
    List<BedAssignment> findCurrentlyAssignedBeds();

    //for checking patient is currently assigned to bed or not
    @Query("SELECT COUNT(ba) FROM BedAssignment ba WHERE ba.patient.id = :patientId AND ba.releasedAt IS NULL")
    long countActiveAssignmentsForPatient(@Param("patientId") Long patientId);



}
