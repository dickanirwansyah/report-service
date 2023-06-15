package id.corp.app.reportservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "phone_number", nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @Column(name = "address", nullable = false, length = 200)
    private String address;

    @Column(name = "twitter", nullable = false, unique = true, length = 100)
    private String twitter;

    @Column(name = "status", nullable = false, length = 50)
    private String status;
}
