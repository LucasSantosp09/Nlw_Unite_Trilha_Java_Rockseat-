package codepause.com.br.passin.dto.events.exceptions;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message){
        super(message);
    }
}
