package ge.ticketebi.ticketebi_backend.services;

import ge.ticketebi.ticketebi_backend.domain.dto.*;
import ge.ticketebi.ticketebi_backend.domain.entities.*;
import ge.ticketebi.ticketebi_backend.exceptions.ResourceNotFoundException;
import ge.ticketebi.ticketebi_backend.exceptions.UnauthorizedActionException;
import ge.ticketebi.ticketebi_backend.mappers.Mapper;
import ge.ticketebi.ticketebi_backend.mappers.ReqResMapper;
import ge.ticketebi.ticketebi_backend.repositories.CategoryRepository;
import ge.ticketebi.ticketebi_backend.repositories.EventRepository;
import ge.ticketebi.ticketebi_backend.repositories.LocationRepository;
import ge.ticketebi.ticketebi_backend.services.impl.EventServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock private EventRepository eventRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private LocationRepository locationRepository;
    @Mock private ReqResMapper<EventEntity, EventRequest, EventResponse> eventMapper;
    @Mock private Mapper<CategoryEntity, CategoryDto> categoryMapper;
    @Mock private Mapper<LocationEntity, LocationDto> locationMapper;

    @InjectMocks private EventServiceImpl eventService;

    private CategoryEntity categoryEntity;
    private CategoryDto categoryDto;

    private LocationEntity locationEntity;
    private LocationDto locationDto;

    private EventEntity eventEntity;
    private EventRequest eventRequest;
    private EventUpdateRequest eventUpdateRequest;
    private EventResponse eventResponse;

    @BeforeEach
    void setUp() {
        // -------- CATEGORY --------
        categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("Music")
                .build();

        categoryDto = CategoryDto.builder()
                .name("Music")
                .build();

        // -------- LOCATION --------
        locationEntity = LocationEntity.builder()
                .id(1L)
                .name("Tbilisi Concert Hall")
                .address("Rustaveli Ave 1")
                .city("Tbilisi")
                .latitude(41.7151)
                .longitude(44.8271)
                .build();

        locationDto = LocationDto.builder()
                .name("Tbilisi Concert Hall")
                .address("Rustaveli Ave 1")
                .city("Tbilisi")
                .latitude(41.7151)
                .longitude(44.8271)
                .build();

        // -------- EVENT REQUEST (used for creating event) --------
        eventRequest = EventRequest.builder()
                .title("Rock Festival")
                .description("Annual rock music festival")
                .date(LocalDateTime.of(2025, 10, 20, 18, 0))
                .location(locationDto)
                .categories(Set.of(categoryDto))
                .build();

        // -------- EVENT ENTITY (as it exists in DB) --------
        eventEntity = EventEntity.builder()
                .id(100L)
                .title("Rock Festival")
                .description("Annual rock music festival")
                .date(LocalDateTime.of(2025, 10, 20, 18, 0))
                .location(locationEntity)
                .categories(Set.of(categoryEntity))
                .deleted(false)
                .build();

        // -------- EVENT UPDATE REQUEST (used for update tests) --------
        eventUpdateRequest = EventUpdateRequest.builder()
                .title("Updated Rock Festival")
                .description("Updated description")
                .date(LocalDateTime.of(2025, 11, 5, 19, 0))
                .location(locationDto)
                .categories(Set.of(categoryDto))
                .build();

        // -------- RESPONSE DTO --------
        eventResponse = EventResponse.builder()
                .id(100L)
                .title("Rock Festival")
                .description("Annual rock music festival")
                .date(LocalDateTime.of(2025, 10, 20, 18, 0))
                .location(locationDto)
                .categories(Set.of(categoryDto))
                .thumbnailUrl(null)
                .build();
    }

    @Test
    void getEvents_shouldReturnMappedList() {
        when(eventRepository.findAllWithDetails()).thenReturn(List.of(eventEntity));
        when(eventMapper.toResponseDto(eventEntity)).thenReturn(eventResponse);

        List<EventResponse> result = eventService.getEvents();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(eventResponse.getId());
        verify(eventRepository).findAllWithDetails();
        verify(eventMapper).toResponseDto(eventEntity);
    }

    @Test
    void myEvents_shouldReturnMappedListForOrganizer() {
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ORGANIZER);

        when(eventRepository.findByOrganizerWithDetails(organizer)).thenReturn(List.of(eventEntity));
        when(eventMapper.toResponseDto(eventEntity)).thenReturn(eventResponse);

        List<EventResponse> result = eventService.myEvents(organizer);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getId()).isEqualTo(eventResponse.getId());
        verify(eventRepository).findByOrganizerWithDetails(organizer);
        verify(eventMapper).toResponseDto(eventEntity);
    }

    @Test
    void getEventById_shouldReturnMappedEntity_whenFound() {
        when(eventRepository.findByIdAndDeletedFalse(eventEntity.getId()))
                .thenReturn(Optional.of(eventEntity));
        when(eventMapper.toResponseDto(eventEntity)).thenReturn(eventResponse);

        EventResponse result = eventService.getEventById(eventEntity.getId());

        assertThat(result.getId()).isEqualTo(eventResponse.getId());
        verify(eventRepository).findByIdAndDeletedFalse(eventEntity.getId());
        verify(eventMapper).toResponseDto(eventEntity);
    }

    @Test
    void getEventById_shouldThrow_whenNotFound() {
        when(eventRepository.findByIdAndDeletedFalse(eventEntity.getId() + 10))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.getEventById(eventEntity.getId() + 10))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Event not found");
    }

    @Test
    void createEvent_shouldMapAndSave() {
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ORGANIZER);

        when(eventMapper.toEntity(eventRequest)).thenReturn(eventEntity);
        when(categoryRepository.findByName(categoryDto.getName())).thenReturn(Optional.of(categoryEntity));
        when(locationRepository.findByName(locationDto.getName())).thenReturn(Optional.empty());
        when(locationRepository.save(eventEntity.getLocation())).thenReturn(locationEntity);
        when(eventRepository.save(eventEntity)).thenReturn(eventEntity);
        when(eventMapper.toResponseDto(eventEntity)).thenReturn(eventResponse);

        EventResponse result = eventService.createEvent(eventRequest, organizer);

        assertThat(result.getId()).isEqualTo(eventResponse.getId());
        assertThat(eventEntity.getOrganizer()).isEqualTo(organizer);
        assertThat(eventEntity.getCategories()).contains(categoryEntity);
        assertThat(eventEntity.getLocation()).isEqualTo(locationEntity);
        verify(eventMapper).toEntity(eventRequest);
        verify(categoryRepository).findByName(categoryDto.getName());
        verify(locationRepository).findByName(locationDto.getName());
        verify(locationRepository).save(eventEntity.getLocation());
        verify(eventRepository).save(eventEntity);
        verify(eventMapper).toResponseDto(eventEntity);
    }

    @Test
    void createEvent_shouldThrow_whenCategoryNotFound() {
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ORGANIZER);

        when(eventMapper.toEntity(eventRequest)).thenReturn(eventEntity);
        when(categoryRepository.findByName(categoryDto.getName())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.createEvent(eventRequest, organizer))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found");

        verify(eventMapper).toEntity(eventRequest);
        verify(categoryRepository).findByName(categoryDto.getName());
        verify(eventRepository, never()).save(any());
    }

    @Test
    void deleteEvent_shouldSoftDelete_whenOrganizerIsAllowed() {
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ORGANIZER);
        eventEntity.setOrganizer(organizer);

        when(eventRepository.findByIdAndDeletedFalse(eventEntity.getId()))
                .thenReturn(Optional.of(eventEntity));
        when(eventRepository.save(eventEntity)).thenReturn(eventEntity);

        MessageResponse result = eventService.deleteEvent(eventEntity.getId(), organizer);

        assertThat(eventEntity.isDeleted()).isTrue();
        assertThat(result.getMessage()).isEqualTo("Event deleted");
        verify(eventRepository).save(eventEntity);
        verify(eventRepository).findByIdAndDeletedFalse(eventEntity.getId());
    }

    @Test
    void deleteEvent_shouldThrow_whenEventNotFound() {
        User organizer = new User();
        organizer.setId(1L);
        organizer.setRole(Role.ORGANIZER);

        when(eventRepository.findByIdAndDeletedFalse(eventEntity.getId() + 10))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> eventService.deleteEvent(eventEntity.getId() + 10, organizer))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("event not found");

        verify(eventRepository).findByIdAndDeletedFalse(eventEntity.getId() + 10);
        verify(eventRepository, never()).save(any());
    }

    @Test
    void deleteEvent_shouldThrow_whenUnauthorized() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setRole(Role.ORGANIZER);

        User originalOrganizer = new User();
        originalOrganizer.setId(1L);
        originalOrganizer.setRole(Role.ORGANIZER);
        eventEntity.setOrganizer(originalOrganizer);

        when(eventRepository.findByIdAndDeletedFalse(eventEntity.getId()))
                .thenReturn(Optional.of(eventEntity));

        assertThatThrownBy(() -> eventService.deleteEvent(eventEntity.getId(), anotherUser))
                .isInstanceOf(UnauthorizedActionException.class)
                .hasMessageContaining("You are not allowed to delete this event");

        verify(eventRepository).findByIdAndDeletedFalse(eventEntity.getId());
        verify(eventRepository, never()).save(any());
    }

    void updateEvent_shouldUpdateFields_whenOrganizerIsAllowed() {

    }

    void updateEvent_shouldThrow_whenEventNotFound() {

    }

    void updateEvent_shouldThrow_whenUnauthorized() {

    }

    void updateEvent_shouldUpdateLocation_whenProvided() {

    }

    void updateEvent_shouldUpdateCategories_whenProvided() {

    }

    void uploadThumbnail_shouldSaveFileAndReturnUrl_whenOrganizerIsAllowed() {

    }

    void uploadThumbnail_shouldThrow_whenFileIsEmpty() {

    }

    void uploadThumbnail_shouldThrow_whenEventNotFound() {

    }

    void uploadThumbnail_shouldThrow_whenUnauthorized() {

    }

}
