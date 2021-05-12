package com.epam.training.ticketservice.mappers;

import com.epam.training.ticketservice.dataaccess.entities.ScreeningEntity;
import com.epam.training.ticketservice.domain.user.Screening;

public interface ScreeningEntityMapper {
    Screening mapScreeningEntity(ScreeningEntity screeningEntity);
}
