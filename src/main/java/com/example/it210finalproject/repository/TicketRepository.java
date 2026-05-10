package com.example.it210finalproject.repository;

import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.dto.Top5User;
import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("""
            select t from  Ticket  t
            where t.id = :id
            """)
    Page<Ticket> searchById(@Param("id") Long id, Pageable pageable);

    Page<Ticket> findByUser(User user, Pageable pageable);

    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);

    @Query("select sum (t.price) from Ticket t where t.status = PAID")
    Double getRevenue();

    Integer countByStatus(TicketStatus status);

    @Query("""
            select new com.example.it210finalproject.model.dto.Top5User(
                t.user,
                count(t)
            ) from Ticket t
            where t.status = com.example.it210finalproject.enums.TicketStatus.PAID
            group by t.user
            order by count(t) desc
            """)
    List<Top5User> getTop5Users(Pageable pageable);
}
