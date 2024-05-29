package com.example.heroesbackend.entities;

import com.example.heroesbackend.enums.Publisher;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Hero implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NonNull
    @Column(unique = true, nullable = false)
    private String name;

    @NotNull
    @NonNull
    @Enumerated(EnumType.STRING)
    private Publisher publisher;

    @NotEmpty
    @NonNull
    private String alterEgo;

    @NotEmpty
    @NonNull
    private String firstApparition;

    @ElementCollection
    @NotNull
    private List<String> characters;

    private String urlAvatar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hero hero = (Hero) o;
        return id != null && Objects.equals(id, hero.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
