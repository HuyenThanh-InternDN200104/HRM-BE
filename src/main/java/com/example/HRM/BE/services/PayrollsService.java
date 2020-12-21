package com.example.HRM.BE.services;

import com.example.HRM.BE.DTO.DatePayrollDetail;
import com.example.HRM.BE.DTO.Payroll;
import com.example.HRM.BE.DTO.Profile;
import com.example.HRM.BE.converters.Bases.Converter;
import com.example.HRM.BE.entities.DatePayrollDetailEntity;
import com.example.HRM.BE.entities.UserEntity;
import com.example.HRM.BE.repositories.DateDetailPayrollRepository;
import com.example.HRM.BE.repositories.HolidayRepository;
import com.example.HRM.BE.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    DateDetailPayrollRepository dateDetailPayrollRepository;

    @Autowired
    private Converter<UserEntity, Profile> userEntityToProfile;

    public List<Payroll> getPayrollsFollowMonth(int month) {
        List<Payroll> payrolls = new ArrayList<>();
        List<UserEntity> userEntitiesFromDB = this.userRepository.findAll();

        for (UserEntity userEntity : userEntitiesFromDB) {
            List<DatePayrollDetailEntity> datePayrollDetails
                    = this.dateDetailPayrollRepository.findByUserEntityEmail(userEntity.getEmail()).stream().filter(datePayrollDetailEntity -> getMonth(datePayrollDetailEntity.getTimeCheckIn()) == month)
                    .collect(Collectors.toList());

            Payroll payroll = new Payroll();

            System.out.printf("AAA "+ datePayrollDetails.size());
            System.out.println("BBBB" +userEntity.getHourlyWages());
            payroll.setSalary(
                    datePayrollDetails.size() * userEntity.getHourlyWages() * 100000
            );
            payroll.setProfile(userEntityToProfile.convert(userEntity));

            payrolls.add(payroll);
        }
        return payrolls;
    }

    public int getMonth(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        return month;
    }
}
