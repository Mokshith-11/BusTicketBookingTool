package fleet.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="bus")
@Data
public class Bus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer  busId;
    private String busName;


}
