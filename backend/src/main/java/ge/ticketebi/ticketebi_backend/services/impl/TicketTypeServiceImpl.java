package ge.ticketebi.ticketebi_backend.services.impl;

import ge.ticketebi.ticketebi_backend.domain.dto.TicketTypeRequest;
import ge.ticketebi.ticketebi_backend.domain.dto.TicketTypeResponse;
import ge.ticketebi.ticketebi_backend.domain.entities.EventEntity;
import ge.ticketebi.ticketebi_backend.domain.entities.TicketTypeEntity;
import ge.ticketebi.ticketebi_backend.domain.entities.User;
import ge.ticketebi.ticketebi_backend.exceptions.ResourceNotFoundException;
import ge.ticketebi.ticketebi_backend.exceptions.UnauthorizedActionException;
import ge.ticketebi.ticketebi_backend.mappers.ReqResMapper;
import ge.ticketebi.ticketebi_backend.repositories.EventRepository;
import ge.ticketebi.ticketebi_backend.repositories.TicketTypeRepository;
import ge.ticketebi.ticketebi_backend.services.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final EventRepository eventRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final ReqResMapper<TicketTypeEntity, TicketTypeRequest, TicketTypeResponse> ticketTypeMapper;

    @Override
    public TicketTypeResponse addTicketType(TicketTypeRequest ticketType, Long eventId, User organizer) {
        EventEntity event = eventRepository.findByIdAndDeletedFalse(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event doesn't exist")
        );
        if(!event.getOrganizer().getId().equals(organizer.getId())){
            throw new UnauthorizedActionException("You don't have permission to perform this action");
        }

        TicketTypeEntity ticketTypeEntity = ticketTypeMapper.toEntity(ticketType);
        ticketTypeEntity.setEvent(event);

        ticketTypeRepository.save(ticketTypeEntity);

        return ticketTypeMapper.toResponseDto(ticketTypeEntity);
    }
}
