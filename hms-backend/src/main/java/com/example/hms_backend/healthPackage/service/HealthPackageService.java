package com.example.hms_backend.healthPackage.service;


import com.example.hms_backend.healthPackage.dto.HealthPackageDTO;
import com.example.hms_backend.healthPackage.dto.HealthPackageResponseDTO;
import com.example.hms_backend.healthPackage.entity.HealthPackage;
import com.example.hms_backend.healthPackage.repository.HealthPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class HealthPackageService {
    @Autowired
    private HealthPackageRepository repository;

    public List<HealthPackageResponseDTO> getAll() {
        return repository.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    public Optional<HealthPackageDTO> getById(Long id) {
        return repository.findById(id).map(this::toDTO);
    }

    public void create(HealthPackageDTO dto) {
        try {
            HealthPackage entity = dtoToEntity(dto);
            HealthPackage saved = repository.save(entity);
            toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error creating health package: " + e.getMessage());
        }

    }

    public void update(Long id, HealthPackageDTO dto) {
        try {

            // ------- Old approach (KEEPING FOR REFERENCE, DO NOT REMOVE) -------
        /*
        repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setPrice(dto.getPrice());
            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                try {
                    existing.setImageData(dto.getImage().getBytes());
                    existing.setContentType(dto.getImage().getContentType());
                } catch (Exception e) {
                    System.err.println("Error converting icon: " + e.getMessage());
                }
            }
            return toDTO(repository.save(existing));
        });
        */
            // --------------------------------------------------------------------

            HealthPackage hp = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Health package with id: " + id + " not found"));

            hp.setName(dto.getName());
            hp.setDescription(dto.getDescription());
            hp.setPrice(dto.getPrice());

            // ✅ Correct image update check
            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                try {
                    hp.setImageData(dto.getImage().getBytes());
                    hp.setContentType(dto.getImage().getContentType());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read image file");
                }
            }
            System.out.println(hp.toString());
            repository.save(hp);

        } catch (Exception e) {
            throw new RuntimeException("Error updating health package: " + e.getMessage());
        }
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }


    //entity to dto
    private HealthPackageDTO toDTO(HealthPackage entity) {
        HealthPackageDTO dto = new HealthPackageDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());


        return dto;
    }

    private HealthPackageResponseDTO entityToDTO(HealthPackage entity) {
        HealthPackageResponseDTO dto = new HealthPackageResponseDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());

        if (entity.getImageData() != null) {
            dto.setImage(
                    "data:" + entity.getContentType() + ";base64," +
                            Base64.getEncoder().encodeToString(entity.getImageData())
            );
        }

        return dto;
    }

    private HealthPackage dtoToEntity(HealthPackageDTO dto) {
        HealthPackage entity = new HealthPackage();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());

        if (dto.getImage() != null || !dto.getImage().isEmpty()) {
            try {
                entity.setImageData(dto.getImage().getBytes());
                entity.setContentType(dto.getImage().getContentType());
            } catch (Exception e) {
                System.err.println("Error converting icon: " + e.getMessage());
            }
        }
        return entity;
    }

    public boolean existsByNameIgnoreCase(String name)
    {
       return repository.existsByNameIgnoreCase(name);
    }
}
