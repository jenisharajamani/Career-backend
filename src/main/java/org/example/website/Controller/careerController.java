package org.example.website.Controller;

import org.example.website.Model.Career;
import org.example.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class careerController {

    @Autowired
    private UserService userService;

    // ✅ SAVE WITH DUPLICATE CHECK
    @PostMapping("/saveCareer")
    public ResponseEntity<?> saveCareer(@RequestBody Career career) {

        System.out.println("===== SAVE API CALLED =====");
        System.out.println("Incoming Data: " + career.getName() + " | " + career.getDob());

        boolean exists = userService.existsByNameAndDob(
                career.getName(),
                career.getDob()
        );

        if (exists) {
            System.out.println("❌ DUPLICATE ENTRY FOUND");
            return ResponseEntity
                    .badRequest()
                    .body("You've already submitted the form");
        }

        Career saved = userService.saveUser(career);

        System.out.println("✅ DATA SAVED SUCCESSFULLY: " + saved.getName());

        return ResponseEntity.ok(saved);
    }

    // ✅ GET ALL DATA
    @GetMapping("/all")
    public List<Career> getAllUsers() {
        System.out.println("===== FETCH ALL USERS API CALLED =====");
        return userService.getAllCareers();
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        System.out.println("===== DELETE API CALLED FOR ID: " + id + " =====");
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // ✅ UPDATE API (FIXED)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody Career request) {

        System.out.println("===== UPDATE API CALLED =====");
        System.out.println("Updating ID: " + id);

        Career updated = userService.updateFullUser(id, request);

        if (updated == null) {
            System.out.println("❌ USER NOT FOUND");
            return ResponseEntity.badRequest().body("User not found");
        }

        System.out.println("✅ UPDATE SUCCESSFUL FOR: " + updated.getName());

        return ResponseEntity.ok(updated);
    }

    // ✅ PAGINATION API
    @GetMapping("/paginated")
    public ResponseEntity<?> getPaginatedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        System.out.println("===== PAGINATION API CALLED ===== Page: " + page);
        return ResponseEntity.ok(userService.getPaginatedUsers(page, size));
    }
}