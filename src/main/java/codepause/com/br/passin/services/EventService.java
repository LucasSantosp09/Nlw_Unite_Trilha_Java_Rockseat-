package codepause.com.br.passin.services;

import codepause.com.br.passin.domain.attendee.Attendee;
import codepause.com.br.passin.domain.event.Event;
import codepause.com.br.passin.dto.events.EventDetailDTO;
import codepause.com.br.passin.dto.events.EventIdDTO;
import codepause.com.br.passin.dto.events.EventRequestDTO;
import codepause.com.br.passin.dto.events.EventResponseDTO;
import codepause.com.br.passin.dto.events.exceptions.EventNotFoundException;
import codepause.com.br.passin.repositories.AttendeeRepository;
import codepause.com.br.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEvenetDetail(String eventId){
        Event event = this.eventRepository.findById(eventId).orElseThrow( () -> new EventNotFoundException("Event not found with ID: " + eventId));
        List<Attendee> attendeeList = this.attendeeRepository.findByEventId(eventId);
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

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text,Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

}
