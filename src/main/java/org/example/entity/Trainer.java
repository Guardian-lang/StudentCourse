package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(exclude = "trainerCourses")
@Builder
@Entity
@Table(name = "trainer", schema = "itacademy")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "trainer")
    private List<TrainerCourse> trainerCourses = new ArrayList<>();
}
