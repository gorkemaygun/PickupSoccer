package com.soccer.pickupSoccer.utils.token;

import com.soccer.pickupSoccer.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class JwtToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private Date expiresAt;
    @ManyToOne
    @JoinColumn(nullable = false,name="user_id")
    private User user;
    private LocalDateTime confirmedAt;

    public JwtToken(String token){
        this.setToken(token);
    }
    public JwtToken(String token, LocalDateTime createdAt, Date expiresAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user=user;
    }

}
