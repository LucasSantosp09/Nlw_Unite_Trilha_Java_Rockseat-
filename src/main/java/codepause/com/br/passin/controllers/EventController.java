package codepause.com.br.passin.controllers;

import codepause.com.br.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private final EventService service;

    @GetMapping("/{id}")
    public ResponseEntity<String> getEvent(@PathVariable String id){
        this.service.getEvenetDetail(id);
        return ResponseEntity.ok("sucesso!");
    }
}
