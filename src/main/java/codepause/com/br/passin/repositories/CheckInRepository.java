package codepause.com.br.passin.repositories;

import codepause.com.br.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository <CheckIn, Integer> {


    Optional<CheckIn> findByAttendeeId(String attendeeId);
}
