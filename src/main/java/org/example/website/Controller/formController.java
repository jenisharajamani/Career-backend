package org.example.website.Controller;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/form")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class formController {

    @PostMapping("/submit")
    public String submitForm(@RequestBody Map<String, String> data) {
        System.out.println(data);
        return "Success";
    }
}
