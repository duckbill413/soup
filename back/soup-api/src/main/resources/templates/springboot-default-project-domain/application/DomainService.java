package :domain-package-name.application;

import :domain-package-name.dao.:domain-class-nameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class :domain-class-nameService {
    private final :domain-class-nameRepository :domain-method-nameRepository;

}
