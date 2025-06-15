package app.base;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionService {
    private final VersionRepository baseRepository;

    public Version next() {
        Version version = Version.versionStart();
        return baseRepository.save(version);
    }

    public Version now() {
        return baseRepository.findAll()
                .stream()
                .findFirst()
                .orElse(Version.versionStart());
    }
}
