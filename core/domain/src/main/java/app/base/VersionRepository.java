package app.base;

import app.base.Version.VersionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, VersionId> {
}
