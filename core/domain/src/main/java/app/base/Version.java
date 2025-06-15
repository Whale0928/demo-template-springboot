package app.base;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Version {

    @EmbeddedId
    private VersionId id;

    @Embeddable
    public record VersionId(
            int major,
            int minor,
            int patch
    ) {
    }

    public static Version versionStart() {
        return new Version(new VersionId(0, 0, 1));
    }
}
