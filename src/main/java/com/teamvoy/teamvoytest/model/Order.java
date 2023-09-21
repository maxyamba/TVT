package com.teamvoy.teamvoytest.model;

import com.teamvoy.teamvoytest.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "order")
    private Set<IPhoneOrder> iPhoneOrders;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "totalPrice")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private OrderStatus status;
}
