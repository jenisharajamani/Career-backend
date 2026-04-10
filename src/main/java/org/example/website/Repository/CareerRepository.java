package org.example.website.Repository;

import org.example.website.Model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    // ✅ ADD THIS METHOD (for duplicate check)
    boolean existsByNameAndDob(String name, Date dob);
}
