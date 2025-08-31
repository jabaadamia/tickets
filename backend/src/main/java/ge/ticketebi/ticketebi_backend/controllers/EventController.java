package ge.ticketebi.ticketebi_backend.controllers;

import ge.ticketebi.ticketebi_backend.domain.dto.EventRequest;
import ge.ticketebi.ticketebi_backend.domain.dto.EventResponse;
import ge.ticketebi.ticketebi_backend.domain.dto.EventUpdateRequest;
import ge.ticketebi.ticketebi_backend.domain.dto.MessageResponse;
import ge.ticketebi.ticketebi_backend.domain.entities.User;
import ge.ticketebi.ticketebi_backend.services.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody @Valid EventRequest eventRequest,
            @AuthenticationPrincipal User organiser
    ) {
        EventResponse event = eventService.createEvent(eventRequest, organiser);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents() {
        List<EventResponse> events = eventService.getEvents();
        return ResponseEntity.ok(events);
    }

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @GetMapping("/my")
    public ResponseEntity<List<EventResponse>> myEvents(@AuthenticationPrincipal User organizer) {
        List<EventResponse> events = eventService.myEvents(organizer);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        EventResponse event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteEvent(
            @PathVariable Long id,
            @AuthenticationPrincipal User organiser
    ) {
        MessageResponse response = eventService.deleteEvent(id, organiser);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @RequestBody @Valid EventUpdateRequest updateRequest,
            @AuthenticationPrincipal User organiser
    ) {
        EventResponse updated = eventService.updateEvent(id, updateRequest, organiser);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ORGANIZER') or hasRole('ADMIN')")
    @PostMapping("/{id}/thumbnail")
    public ResponseEntity<MessageResponse> uploadEventThumbnail(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file,
            @AuthenticationPrincipal User organiser
    ) {
        MessageResponse imageUrl = eventService.uploadThumbnail(id, file, organiser);
        return ResponseEntity.ok(imageUrl);
    }
}
