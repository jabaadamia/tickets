package ge.ticketebi.ticketebi_backend.repositories;

import ge.ticketebi.ticketebi_backend.domain.entities.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void cleanUp() {
        categoryRepository.deleteAll(); // ensures clean state for every test
    }

    @Test
    void testSaveCategory() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Sports");

        CategoryEntity saved = categoryRepository.save(category);
        assertEquals("Sports", saved.getName());
    }

    @Test
    void testFindCategoryById() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Music");
        categoryRepository.save(category);

        Optional<CategoryEntity> found = categoryRepository.findById("Music");

        assertTrue(found.isPresent());
        assertEquals("Music", found.get().getName());
    }

    @Test
    void testUpdateCategory() {
        CategoryEntity category = new CategoryEntity();
        category.setName("News");
        categoryRepository.save(category);

        // Update the same entity (in your case, name is ID, so nothing changes unless entity has other fields)
        // For this example, assume you added a "description" field or something else modifiable

        // This is just a placeholder - currently, you can't update name since it's @Id
        // In real-world you'd update a non-ID field

        // Example: category.setDescription("Updated"); then save again

        CategoryEntity updated = categoryRepository.save(category);
        assertEquals("News", updated.getName());
    }

    @Test
    void testDeleteCategory() {
        CategoryEntity category = new CategoryEntity();
        category.setName("Gaming");
        categoryRepository.save(category);

        categoryRepository.deleteById("Gaming");

        Optional<CategoryEntity> deleted = categoryRepository.findById("Gaming");
        assertFalse(deleted.isPresent());
    }

    @Test
    void testFindAllCategories() {
        CategoryEntity cat1 = new CategoryEntity();
        cat1.setName("Tech");

        CategoryEntity cat2 = new CategoryEntity();
        cat2.setName("Travel");

        categoryRepository.save(cat1);
        categoryRepository.save(cat2);

        Iterable<CategoryEntity> all = categoryRepository.findAll();
        assertTrue(all.iterator().hasNext());
    }
}
