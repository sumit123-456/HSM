package com.example.hms_backend.finance.invoice.entity.childEntity;

import com.example.hms_backend.RoomAndBedManager.entity.Room;
import com.example.hms_backend.finance.invoice.entity.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "invoice_room")

public class InvoiceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;     // Existing table

    private int days;
    private Double pricePerDay;
    private Double totalPrice;
}
