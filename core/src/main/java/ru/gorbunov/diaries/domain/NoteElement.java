package ru.gorbunov.diaries.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

/**
 * Note element entity.
 *
 * @author Gorbunov.ia
 */
@Entity
@Table(name = "t_note_element")
public class NoteElement extends GeneralEntity implements Swappable {

    /**
     * Note.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id", nullable = false)
    private Note note = null;

    /**
     * Note element description.
     */
    @Column(nullable = false, length = 64)
    private String description;

    /**
     * Sorting order.
     */
    @Column(name = "sort_by", nullable = false)
    private Integer sortBy = 0;

    /**
     * Date of Last Modified.
     */
    @Column(name = "last_modified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getSortBy() {
        return sortBy;
    }

    @Override
    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(getId());
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
        final NoteElement other = (NoteElement) obj;
        return Objects.equals(getId(), other.getId());
    }

}
