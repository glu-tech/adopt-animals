package com.glutech.adoptanimals.service;

import com.glutech.adoptanimals.dto.AnimalDTO;
import com.glutech.adoptanimals.model.Animal;
import com.glutech.adoptanimals.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Page<AnimalDTO> listAnimals(Pageable pageable) {
        Page<Animal> animalPage = animalRepository.findAll(pageable);
        List<AnimalDTO> animalDTOs = animalPage.getContent().stream()
                .map(animal -> new AnimalDTO(
                        animal.getId(),
                        animal.getName(),
                        animal.getDescription(),
                        animal.getUrlImage(),
                        animal.getCategory(),
                        animal.getBirthDate(),
                        animal.getStatus()
                ))
                .collect(Collectors.toList());
        return new PageImpl<>(animalDTOs, pageable, animalPage.getTotalElements());
    }

    public AnimalDTO getAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));
        return new AnimalDTO(
                animal.getId(),
                animal.getName(),
                animal.getDescription(),
                animal.getUrlImage(),
                animal.getCategory(),
                animal.getBirthDate(),
                animal.getStatus()
        );
    }

    public Animal changeStatus(Long id, String status) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal not found"));
        animal.setStatus(status);
        return animalRepository.save(animal);
    }
}
