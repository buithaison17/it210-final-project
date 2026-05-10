package com.example.it210finalproject.repository;

import com.example.it210finalproject.model.dto.Top5Trip;
import com.example.it210finalproject.model.entity.Bus;
import com.example.it210finalproject.model.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    @Query("""
                SELECT t FROM Trip t
                WHERE
                    (:pickUp IS NULL OR t.route.origin.id = :pickUp)
                AND
                    (:dropOff IS NULL OR t.route.destination.id = :dropOff)
                AND
                    (:company IS NULL OR t.bus.company LIKE (CONCAT('%', :company, '%')))
            """)
    Page<Trip> searchTrips(
            @Param("pickUp") Long pickUp,
            @Param("dropOff") Long dropOff,
            @Param("company") String company,
            Pageable pageable
    );

    Trip findByBusIdAndStartTime(Long busId, LocalDateTime startTime);

    boolean existsByBusId(Long busId);

    @Query("""
            select new com.example.it210finalproject.model.dto.Top5Trip(
                        t.route,
                        t.startTime,
                        count(ti),
                        sum(t.price)
                    )
                    from Ticket ti
                    join ti.trip t
                    where ti.status = com.example.it210finalproject.enums.TicketStatus.PAID
                    group by t.route, t.startTime
                    order by sum(t.price) desc
            """)
    List<Top5Trip> top5Trip();
}
