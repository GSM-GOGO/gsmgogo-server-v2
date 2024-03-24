package team.gsmgogo.domain.match.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "match")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;


}
