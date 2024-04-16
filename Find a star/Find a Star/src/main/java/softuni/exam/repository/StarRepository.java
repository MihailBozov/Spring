package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Star;

import java.util.List;
import java.util.Optional;

// TODO:
@Repository
public interface StarRepository extends JpaRepository<Star, Long> {
    
    Optional<Star> findByName(String name);
    
    @Query("SELECT s FROM Star AS s WHERE s.starType = 'RED_GIANT' AND s.observers.size = 0 ORDER BY s.lightYears ASC")
    List<Star> exportStars();
}
