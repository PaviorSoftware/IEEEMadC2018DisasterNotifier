package gr.teicm.ieee.madc.disasternotifierservice.service.commons;

public interface DateService {
    Long getCurrentTime();

    Long getTimeAfterHours(Integer hours);

    boolean isExpired(Long date);
}
