package ge.ticketebi.ticketebi_backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {

    private Long id;

    private String name;

    private String address;

    private String city;

    private Double latitude;

    private Double longitude;

}