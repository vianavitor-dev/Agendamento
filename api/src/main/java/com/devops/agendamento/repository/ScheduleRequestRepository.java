package com.devops.agendamento.repository;

import com.devops.agendamento.dto.response.ScheduleRequestCardDTO;
import com.devops.agendamento.mapper.ScheduleRequestDTOMapper;
import com.devops.agendamento.model.Client;
import com.devops.agendamento.model.ScheduleRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleRequestRepository extends CrudRepository<ScheduleRequest, Long> {
    List<ScheduleRequest> findByClient(Client client);

    @Query(value = """
            SELECT s.* FROM schedule_request as s
            WHERE MONTH(s.start_date) = :month
            AND YEAR(s.start_date) = :year
            """, nativeQuery = true)
    List<ScheduleRequest> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    Optional<ScheduleRequest> findByStartDate(LocalDate startDate);

    @Query(value = """
            SELECT NEW java.lang.Boolean(COUNT (s) > 0) FROM ScheduleRequest s
            WHERE s.startDate = :startDate OR s.endDate = :startDate OR s.startDate = :endDate
            """)
    boolean existisInInterval(LocalDate startDate, LocalDate endDate);
}
