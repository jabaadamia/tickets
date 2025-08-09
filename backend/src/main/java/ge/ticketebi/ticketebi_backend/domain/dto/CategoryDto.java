package ge.ticketebi.ticketebi_backend.domain.dto;

import ge.ticketebi.ticketebi_backend.domain.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String name;

}
