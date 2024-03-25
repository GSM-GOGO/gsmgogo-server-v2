package team.gsmgogo.domain.team.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.team.enums.TeamType;
import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;

@Entity
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_type")
    private TeamType teamType;

    @Column(name = "win_count")
    private Integer winCount;

    @Column(name = "is_survived")
    private Boolean isSurvived;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_grade")
    private GradeEnum teamGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_class")
    private ClassEnum teamClass;
}
