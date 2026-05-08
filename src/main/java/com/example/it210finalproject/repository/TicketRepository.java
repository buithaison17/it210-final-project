package com.example.it210finalproject.repository;

import com.example.it210finalproject.enums.TicketStatus;
import com.example.it210finalproject.model.entity.Ticket;
import com.example.it210finalproject.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findByUser(User user, Pageable pageable);

    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);
}
