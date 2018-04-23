package ru.gorbunov.diaries.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Note entity.
 *
 * @author Gorbunov.ia
 */
@Entity
@Table(name = "t_Notes")
public class Note extends GeneralEntity {

    /**
     * Owner of a note.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    private User user = null;

    /**
     * Note description.
     */
    @NotNull
    @Size(min = 1, max = 64)
    @Column(nullable = false, length = 64)
    private String description;

    /**
     * Sorting order.
     */
    @Column(nullable = false)
    private Integer sortBy = 0;

    /**
     * Date of Last Modified.
     */
    @Column(name = "LastModified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(getId());
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
        final Note other = (Note) obj;
        return Objects.equals(getId(), other.getId());
    }

}