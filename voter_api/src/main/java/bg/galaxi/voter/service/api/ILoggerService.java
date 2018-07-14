package bg.galaxi.voter.service.api;

import bg.galaxi.voter.model.entity.Logger;

public interface ILoggerService {
    boolean persist(Logger l);
}
