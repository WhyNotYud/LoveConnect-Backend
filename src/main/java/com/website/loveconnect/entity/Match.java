package com.website.loveconnect.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.website.loveconnect.enumpackage.MatchStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonIgnore
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    @JsonIgnore
    private User receiver;

    @CreationTimestamp
    @Column(name = "match_date", nullable = false, updatable = false)
    private Date matchDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MatchStatus status = MatchStatus.PENDING;
}
