package ru.gorbunov.diaries.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Role entity.
 *
 * @author Gorbunov.ia
 */
@Entity
@Table(name = "t_role")
public class Role extends GeneralEntity {

    /**
     * Role description.
     */
    @NotNull
    @Size(min = 6, max = 32)
    @Column(unique = true, nullable = false, length = 32)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        return Objects.equals(getId(), other.getId());
    }

}
