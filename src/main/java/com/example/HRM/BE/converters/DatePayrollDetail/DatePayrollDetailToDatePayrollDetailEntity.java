package com.example.HRM.BE.converters.DatePayrollDetail;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.converters.ProfileConverter.ProfileToUserEntity;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import com.example.HRM.BE.exceptions.UserException.UserNotFoundException;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatePayrollDetailToDatePayrollDetailEntity extends Converter<DatePayrollDetail, DatePayrollDetailEntity> {

    @Autowired
    private ProfileToUserEntity profileToUserEntity;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DatePayrollDetailEntity convert(DatePayrollDetail source) {

        DatePayrollDetailEntity datePayrollDetailEntity = new DatePayrollDetailEntity();

        datePayrollDetailEntity.setId(source.getId());
        datePayrollDetailEntity.setTimeCheckIn(source.getTimeCheckIn());
        datePayrollDetailEntity.setTimeCheckOut(source.getTimeCheckOut());
//        datePayrollDetailEntity.setUserEntity(profileToUserEntity.convert(source.getUser()));
        datePayrollDetailEntity.setUserEntity(
                this.userRepository.findByEmail(source.getUser().getEmail()).orElseThrow(
                        () -> new UserNotFoundException()
                )
        );
        return datePayrollDetailEntity;
    }
}
