package ge.ticketebi.ticketebi_backend.services;

import ge.ticketebi.ticketebi_backend.domain.dto.MessageResponse;
import ge.ticketebi.ticketebi_backend.domain.dto.TicketTypeRequest;
import ge.ticketebi.ticketebi_backend.domain.dto.TicketTypeResponse;
import ge.ticketebi.ticketebi_backend.domain.entities.User;

public interface TicketTypeService {
    TicketTypeResponse addTicketType(TicketTypeRequest ticketType, Long eventId, User organizer);
}
