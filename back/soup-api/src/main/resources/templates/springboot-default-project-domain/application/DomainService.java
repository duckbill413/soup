package :domain-package-name.application;

import :domain-package-name.dao.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class :domain-class-nameService {
    private final :domain-class-nameRepository :domain-method-nameRepository;
}
