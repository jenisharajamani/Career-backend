package org.example.website.Controller;

import org.example.website.Model.Career;
import org.example.website.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/career")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class careerController {

    @Autowired
    private UserService userService;

    // ✅ SAVE WITH DUPLICATE CHECK
    @PostMapping("/saveCareer")
    public ResponseEntity<?> saveCareer(@RequestBody Career career) {

        boolean exists = userService.existsByNameAndDob(
                career.getName(),
                career.getDob()
        );

        if (exists) {
            return ResponseEntity
                    .badRequest()
                    .body("You've already submitted the form");
        }

        Career saved = userService.saveUser(career);
        return ResponseEntity.ok(saved);
    }

    // ✅ GET ALL DATA
    @GetMapping("/all")
    public List<Career> getAllUsers() {
        return userService.getAllCareers();
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // ✅ ✅ FIXED UPDATE API

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody Career request) {

        System.out.println("===== UPDATE API CALLED =====");

        Career updated = userService.updateFullUser(id, request);

        return ResponseEntity.ok(updated);
    }
}
