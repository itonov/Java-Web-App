package org.softuni.webapp.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.webapp.constants.AppConstants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "pictures")
public class Picture implements Serializable {
    private String id;

    private String cloudId;

    public Picture() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = AppConstants.PICTURE_CLOUD_ID_NOT_NULL)
    public String getCloudId() {
        return this.cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }
}
