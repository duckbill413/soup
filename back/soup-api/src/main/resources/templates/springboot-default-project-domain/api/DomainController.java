package :domain-package-name.api;

import :domain-package-name.application.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class :domain-class-nameController {
    private final :domain-class-nameService :domain-method-nameService;
}
