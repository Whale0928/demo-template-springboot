package app;

import app.base.Version;
import app.base.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final VersionService baseService;
    private final Pair<String, String> pair = new Pair<>("first", "second");

    @GetMapping
    public ResponseEntity<?> now() {
        Version now = baseService.now();
        return ResponseEntity.ok(now);
    }

    @GetMapping("/version/up")
    public Version api() {
        return baseService.next();
    }
}
