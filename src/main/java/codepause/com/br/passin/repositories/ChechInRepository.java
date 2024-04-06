package codepause.com.br.passin.repositories;

import codepause.com.br.passin.domain.checkin.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChechInRepository extends JpaRepository <CheckIn, Integer> {
}
