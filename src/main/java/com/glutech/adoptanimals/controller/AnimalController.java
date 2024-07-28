package com.glutech.adoptanimals.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glutech.adoptanimals.dto.AnimalDTO;
import com.glutech.adoptanimals.enums.StatusEnum;
import com.glutech.adoptanimals.model.Animal;
import com.glutech.adoptanimals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AmazonS3 s3Client;

    private final String bucketName = "adopt-animal";

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Animal> createAnimal(@RequestPart("animal") String animalJson,
                                               @RequestPart("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Animal animal;
        try {
            animal = objectMapper.readValue(animalJson, Animal.class);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        File tempFile = convertMultipartFileToFile(file);
        if (tempFile == null) {
            return ResponseEntity.badRequest().build();
        }

        String fileUrl = uploadFileToS3(tempFile);
        if (fileUrl == null) {
            return ResponseEntity.status(500).build();
        }

        animal.setUrlImage(fileUrl);
        animal.setStatus(StatusEnum.AVAILABLE.getValue());
        Animal savedAnimal = animalService.createAnimal(animal);

        tempFile.delete();

        return ResponseEntity.ok(savedAnimal);
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convFile = new File(System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll(" ", "_"));
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return convFile;
    }

    private String uploadFileToS3(File file) {
        String fileName = "animals/" + System.currentTimeMillis() + "_" + file.getName();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    @GetMapping
    public ResponseEntity<Page<AnimalDTO>> listAnimals(Pageable pageable) {
        return ResponseEntity.ok(animalService.listAnimals(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getAnimal(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Animal> changeStatus(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        if (Objects.equals(status, StatusEnum.ADOPTED.getValue())) {
            return ResponseEntity.ok(animalService.changeStatus(id, StatusEnum.ADOPTED.getValue()));
        }
        else {
            return ResponseEntity.status(500).build();
        }
    }
}
