package team.gsmgogo.domain.user.entity;

import team.gsmgogo.domain.user.enums.ClassEnum;
import team.gsmgogo.domain.user.enums.GradeEnum;
import team.gsmgogo.domain.user.enums.Role;
import team.gsmgogo.domain.user.enums.SchoolRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq")
    private Long userSeq;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_grade")
    @Enumerated(EnumType.STRING)
    private GradeEnum userGrade;

    @Column(name = "user_class")
    @Enumerated(EnumType.STRING)
    private ClassEnum userClass;

    @Column(name = "school_role")
    @Enumerated(EnumType.STRING)
    private SchoolRole schoolRole;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "point")
    private Integer point;

}
