package :domain-package-name.api;

import :domain-package-name.application.:domain-class-nameService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Tag(name = ":domain-class-name")
public class :domain-class-nameController {
    private final :domain-class-nameService :domain-method-nameService;

}
