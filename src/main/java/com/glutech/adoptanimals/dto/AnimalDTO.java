package com.glutech.adoptanimals.dto;

import java.time.LocalDate;
import java.time.Period;

public class AnimalDTO {
    private Long id;
    private String name;
    private String description;
    private String urlImage;
    private String category;
    private int age;
    private String status;

    private LocalDate birthDate;

    public AnimalDTO(Long id, String name, String description, String urlImage, String category, LocalDate birthDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.urlImage = urlImage;
        this.category = category;
        this.birthDate = birthDate;
        this.age = calculateAge(birthDate);
        this.status = status;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAge() {
        return calculateAge(birthDate);
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
