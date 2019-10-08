package com.borelanjo.despesas.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.borelanjo.despesas.infrastructure.persistence.hibernate.listener.BaseListener;

@EntityListeners(BaseListener.class)
@MappedSuperclass
public abstract class BaseEntity<ID> implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected ID id;

  @Column(name = "dt_created", nullable = true)
  protected LocalDateTime created;

  @Column(name = "dt_updated", nullable = true)
  protected LocalDateTime updated;

  protected Boolean activated;

  protected Boolean deleted;

  public BaseEntity() {
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getUpdated() {
    return updated;
  }

  public void setUpdated(LocalDateTime updated) {
    this.updated = updated;
  }

  public Boolean getActivated() {
    return activated;
  }

  public void setActivated(Boolean activated) {
    this.activated = activated;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public ID getId() {
    return id;
  }
  
  public void delete() {
    deleted = true;
  }
  
  public void switchActivation() {
    activated = !activated;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BaseEntity<?> other = (BaseEntity<?>) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "BaseEntity [id=" + id + "]";
  }

}
