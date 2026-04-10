package org.example.website.Service;

import org.example.website.Model.Career;
import org.example.website.Repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private CareerRepository careerRepository;

    // ✅ FIXED SAVE METHOD
    public Career saveUser(Career career) {

        // ✅ set audit fields
        career.setCreatedDate(new Date());
        career.setUpdatedDate(new Date());
        career.setCreatedBy("USER"); // you can change later
        career.setUpdatedBy("USER");

        System.out.println("Saving to DB: " + career.getName()); // debug log

        return careerRepository.save(career); // ✅ IMPORTANT
    }

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    // ✅ GET BY ID
    public Career getById(Long id) {
        return careerRepository.findById(id).orElse(null);
    }

    public boolean existsByNameAndDob(String name, Date dob) {
        return careerRepository.existsByNameAndDob(name, dob);
    }

    public void deleteUser(Long id) {
        careerRepository.deleteById(id);
    }

    public Career updateUser(Long id, String updatedBy) {

        Career existing = careerRepository.findById(id).orElse(null);

        if (existing != null) {
            existing.setUpdatedBy(updatedBy);
            existing.setUpdatedDate(new Date());

            return careerRepository.save(existing);
        }

        return null;
    }

    public Career updateFullUser(Long id, Career request) {

        Career career = careerRepository.findById(id).orElse(null);

        if (career != null) {

            // ✅ update fields
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
            career.setUpdatedBy(request.getUpdatedBy());
            career.setUpdatedDate(new Date());

            return careerRepository.save(career);
        }

        return null;
    }
}