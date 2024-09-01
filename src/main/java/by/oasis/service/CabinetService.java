package by.oasis.service;

import by.oasis.service.api.ICabinetService;
import by.oasis.service.api.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CabinetService implements ICabinetService {

    private final IUserService userService;

    public CabinetService(IUserService userService) {
        this.userService = userService;
    }
}
