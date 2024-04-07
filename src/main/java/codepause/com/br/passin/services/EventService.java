package codepause.com.br.passin.services;

import codepause.com.br.passin.domain.event.Event;
import codepause.com.br.passin.dto.events.EventDetailDTO;
import codepause.com.br.passin.dto.events.EventResponseDTO;
import codepause.com.br.passin.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public EventResponseDTO getEvenetDetail(String eventId){
        Event event = this.eventRepository.findById(eventId).orElseThrow( () -> new RuntimeException("Event not found with ID: " + eventId));
        return new EventResponseDTO(event, 0);
    }

}
