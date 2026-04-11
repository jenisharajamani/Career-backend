package org.example.website.Service;

import org.example.website.Model.Career;
import org.example.website.Repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private CareerRepository careerRepository;

    // ✅ FIXED SAVE METHOD
    public Career saveUser(Career career) {

        // ✅ set audit fields
        career.setCreatedDate(LocalDateTime.now());
        career.setUpdatedDate(LocalDateTime.now());

        if (career.getCreatedBy() == null) {
            career.setCreatedBy("USER");
        }

        if (career.getUpdatedBy() == null) {
            career.setUpdatedBy("USER");
        }

        System.out.println("Saving to DB: " + career.getName());

        return careerRepository.save(career);
    }

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    // ✅ GET BY ID
    public Career getById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    // ✅ FIXED TYPE
    public boolean existsByNameAndDob(String name, LocalDate dob) {
        return careerRepository.existsByNameAndDob(name, dob);
    }

    public void deleteUser(Long id) {
        careerRepository.deleteById(id);
    }

    public Career updateUser(Long id, String updatedBy) {

        Career existing = careerRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setUpdatedBy(updatedBy);
            existing.setUpdatedDate(LocalDateTime.now());

            return careerRepository.save(existing);
        }

        return null;
    }

    public Career updateFullUser(Long id, Career request) {

        Career career = careerRepository.findById(id).orElse(null);

        if (career != null) {

            // ✅ update fields safely
            if (request.getName() != null && !request.getName().isEmpty()) {
                career.setName(request.getName());
            }

            if (request.getDegree() != null && !request.getDegree().isEmpty()) {
                career.setDegree(request.getDegree());
            }

            if (request.getExperienceYears() != 0) {
                career.setExperienceYears(request.getExperienceYears());
            }

            // ✅ audit
            if (request.getUpdatedBy() != null) {
                career.setUpdatedBy(request.getUpdatedBy());
            }

            career.setUpdatedDate(LocalDateTime.now());

            return careerRepository.save(career);
        }

        return null;
    }

    // ✅ PAGINATION
    public Page<Career> getPaginatedUsers(int page, int size) {
        return careerRepository.findAll(PageRequest.of(page, size));
    }
}