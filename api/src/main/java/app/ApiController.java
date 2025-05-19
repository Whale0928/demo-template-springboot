package app;

import app.base.Base;
import app.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final BaseService baseService;
    private final Pair<String, String> pair = new Pair<>("first", "second");

    @GetMapping("/api")
    public Base api() {
        return baseService.next();
    }
}
