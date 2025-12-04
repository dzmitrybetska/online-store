package com.upgrade.store.domain.model.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@SuperBuilder(setterPrefix = "with")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@MappedSuperclass
public abstract class DataEntity extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public String toString() {
        return "(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataEntity that = (DataEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
