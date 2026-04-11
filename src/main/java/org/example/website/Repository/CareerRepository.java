package org.example.website.Repository;

import org.example.website.Model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    // ✅ FIXED TYPE
    boolean existsByNameAndDob(String name, LocalDate dob);
}