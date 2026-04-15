package fleet.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="driver")
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer driverId;

    private String driverName;

    private String phoneNumber;
}
