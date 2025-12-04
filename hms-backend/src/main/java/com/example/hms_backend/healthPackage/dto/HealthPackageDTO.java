package com.example.hms_backend.healthPackage.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class  HealthPackageDTO {


    private Long id;

    private String code;

    private String name;

    private String description;

    private Double price;

    private MultipartFile image;


    public MultipartFile getImage() {
        return image;
    }




    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "HealthPackageDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
