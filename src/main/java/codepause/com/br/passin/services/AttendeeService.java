package codepause.com.br.passin.services;

import codepause.com.br.passin.domain.attendee.Attendee;
import codepause.com.br.passin.domain.attendee.exceptions.AttendeeAlreadyExistException;
import codepause.com.br.passin.domain.checkin.CheckIn;
import codepause.com.br.passin.dto.attendee.AttendeeDetails;
import codepause.com.br.passin.dto.attendee.AttendeesListResponseDTO;
import codepause.com.br.passin.repositories.AttendeeRepository;
import codepause.com.br.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;

    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttedee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList.stream().map(attendee -> {
           Optional<CheckIn>  checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
           LocalDateTime checkInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
           return new AttendeeDetails(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkInAt);
        }).toList();

        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

    public Attendee registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public void verifyAttedeeSubscription(String email, String eventId) {
       Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(email, eventId);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee iss already registered");
    }
}
