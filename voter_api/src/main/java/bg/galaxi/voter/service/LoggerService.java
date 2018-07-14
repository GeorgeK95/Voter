/*
package bg.galaxi.voter.service;

import bg.galaxi.voter.model.entity.Logger;
import bg.galaxi.voter.repository.LoggerRepository;
import bg.galaxi.voter.service.api.ILoggerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoggerService implements ILoggerService {

    private final LoggerRepository loggerRepository;

    public LoggerService(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Override
    public boolean persist(Logger l) {
        this.loggerRepository.save(l);

        return true;
    }
}
*/
