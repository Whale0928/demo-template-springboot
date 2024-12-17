package app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseJpaRepository extends BaseRepository, JpaRepository<Base, Long> {
}
