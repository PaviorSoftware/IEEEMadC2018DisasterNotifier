package gr.teicm.ieee.madc.disasternotifierservice.service.commons.impl;

import gr.teicm.ieee.madc.disasternotifierservice.service.commons.DateService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class DateServiceImpl implements DateService {

    private final Calendar calendar;

    public DateServiceImpl() {
        calendar = Calendar.getInstance();
    }

    private Date getDate() {
        return calendar.getTime();
    }

    @Override
    public Long getCurrentTime() {
        return getDate().getTime();
    }

    @Override
    public Long getTimeAfterHours(Integer hours) {
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime().getTime();
    }

    @Override
    public boolean isExpired(Long date) {
        return getCurrentTime() > date;
    }
}
