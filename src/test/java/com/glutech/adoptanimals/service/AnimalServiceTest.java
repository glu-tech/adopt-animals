package com.glutech.adoptanimals.service;

import com.glutech.adoptanimals.dto.AnimalDTO;
import com.glutech.adoptanimals.enums.StatusEnum;
import com.glutech.adoptanimals.model.Animal;
import com.glutech.adoptanimals.repository.AnimalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAnimal_Success() {
        Animal animal = new Animal();
        animal.setName("Dog");
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        Animal result = animalService.createAnimal(animal);
        assertNotNull(result);
        assertEquals("Dog", result.getName());
    }

    @Test
    void listAnimals_Success() {
        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setName("Dog");

        Animal animal2 = new Animal();
        animal2.setId(2L);
        animal2.setName("Cat");

        List<Animal> animals = Arrays.asList(animal1, animal2);
        Page<Animal> animalPage = new PageImpl<>(animals);
        Pageable pageable = PageRequest.of(0, 10);

        when(animalRepository.findAll(pageable)).thenReturn(animalPage);

        Page<AnimalDTO> result = animalService.listAnimals(pageable);
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("Dog", result.getContent().get(0).getName());
        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals("Cat", result.getContent().get(1).getName());
    }

    @Test
    void getAnimal_Success() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Dog");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        AnimalDTO result = animalService.getAnimal(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Dog", result.getName());
    }

    @Test
    void getAnimal_NotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            animalService.getAnimal(1L);
        });

        assertEquals("Animal not found", exception.getMessage());
    }

    @Test
    void changeStatus_Success() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setStatus(StatusEnum.AVAILABLE.getValue());
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(animal)).thenReturn(animal);

        Animal result = animalService.changeStatus(1L, StatusEnum.ADOPTED.getValue());
        assertNotNull(result);
        assertEquals(StatusEnum.ADOPTED.getValue(), result.getStatus());
    }

    @Test
    void changeStatus_AnimalNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            animalService.changeStatus(1L, StatusEnum.ADOPTED.getValue());
        });

        assertEquals("Animal not found", exception.getMessage());
    }
}
