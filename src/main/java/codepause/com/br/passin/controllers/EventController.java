package codepause.com.br.passin.controllers;

import codepause.com.br.passin.dto.attendee.AttendeesListResponseDTO;
import codepause.com.br.passin.dto.events.EventIdDTO;
import codepause.com.br.passin.dto.events.EventRequestDTO;
import codepause.com.br.passin.dto.events.EventResponseDTO;
import codepause.com.br.passin.services.AttendeeService;
import codepause.com.br.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id){
        EventResponseDTO event = this.eventService.getEvenetDetail(id);
        return ResponseEntity.ok(event);
    }
    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
       EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/event/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
       return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id){
        AttendeesListResponseDTO attendeesListResponse = this.attendeeService.getEventsAttedee(id);
        return ResponseEntity.ok(attendeesListResponse);
    }
}
