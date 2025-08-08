package ge.ticketebi.ticketebi_backend.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name= "categories")
public class CategoryEntity {
    @Id
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Event> events = new HashSet<>();

}
