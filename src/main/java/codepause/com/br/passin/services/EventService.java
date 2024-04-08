package codepause.com.br.passin.services;

import codepause.com.br.passin.domain.attendee.Attendee;
import codepause.com.br.passin.domain.event.Event;
import codepause.com.br.passin.domain.event.exceptions.EventFullException;
import codepause.com.br.passin.dto.events.EventIdDTO;
import codepause.com.br.passin.dto.events.EventRequestDTO;
import codepause.com.br.passin.dto.events.EventResponseDTO;
import codepause.com.br.passin.dto.events.exceptions.EventNotFoundException;
import codepause.com.br.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final AttendeeService attendeeService;

    public EventResponseDTO getEvenetDetail(String eventId){
        Event event = this.eventRepository.findById(eventId).orElseThrow( () -> new EventNotFoundException("Event not found with ID: " + eventId));
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendeeds(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));
        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    public void registerAttendeeOnEvent(String eventId){
        this.attendeeService.verifyAttedeeSubscription("", eventId);

        Event event = this.eventRepository.findById(eventId).orElseThrow( () -> new EventNotFoundException("Event not found with ID: " + eventId));
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendeeds() <= attendeeList.size()) throw new EventFullException("Event is full"){

        }
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text,Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
