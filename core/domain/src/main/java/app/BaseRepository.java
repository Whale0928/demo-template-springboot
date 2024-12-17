package app;

import java.util.Optional;

public interface BaseRepository {
    Optional<Base> findById(Long id);

    Base save(Base base);
}
