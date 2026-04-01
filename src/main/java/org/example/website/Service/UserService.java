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

    public Career saveUser(Career career) {
        return null;
    }

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    // ✅ 👉 ADD THIS METHOD
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

            // ✅ update fields from frontend
            // ✅ update name
            if (request.getName() != null && !request.getName().isEmpty()) {
                career.setName(request.getName());
            }

            // ✅ update degree
            if (request.getDegree() != null && !request.getDegree().isEmpty()) {
                career.setDegree(request.getDegree());
            }

            if (request.getExperienceYears() != 0) {
                career.setExperienceYears(request.getExperienceYears());
            }

            // ✅ audit fields
            career.setUpdatedBy(request.getUpdatedBy());
            career.setUpdatedDate(new Date());

            return careerRepository.save(career);
        }

        return null;
    }
}
