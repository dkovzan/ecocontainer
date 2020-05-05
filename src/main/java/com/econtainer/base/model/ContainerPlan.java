package com.econtainer.base.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ContainerPlan")
public class ContainerPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Plant Template id cannot be null")
    private Long planTemplateId;
    @NotNull(message = "Container id cannot be null")
    private Long containerId;

    @NotNull
    private String name;
    private String description;
    @NotNull
    private String plantName;
    private String stage;
    private Timestamp stageStartDate;
    private Timestamp createdOn;
    private Timestamp updatedOn;

    public ContainerPlan() {
    }

    public ContainerPlan(Long id, Long planTemplateId, Long containerId,
                         String name, String description, String plantName,
                         String stage, Timestamp stageStartDate,
                         Timestamp createdOn, Timestamp updatedOn) {
        this.id = id;
        this.planTemplateId = planTemplateId;
        this.containerId = containerId;
        this.name = name;
        this.description = description;
        this.plantName = plantName;
        this.stage = stage;
        this.stageStartDate = stageStartDate;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Long getId() {
        return id;
    }

    public Long getPlanTemplateId() {
        return planTemplateId;
    }

    public void setPlanTemplateId(Long planTemplateId) {
        this.planTemplateId = planTemplateId;
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Timestamp getStageStartDate() {
        return stageStartDate;
    }

    public void setStageStartDate(Timestamp stageStartDate) {
        this.stageStartDate = stageStartDate;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContainerPlan that = (ContainerPlan) o;
        return id.equals(that.id) &&
                planTemplateId.equals(that.planTemplateId) &&
                containerId.equals(that.containerId) &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                plantName.equals(that.plantName) &&
                Objects.equals(stage, that.stage) &&
                Objects.equals(stageStartDate, that.stageStartDate) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, planTemplateId, containerId, name, description, plantName,
                stage, stageStartDate, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return "ContainerPlan{" +
                "id=" + id +
                ", planTemplateId=" + planTemplateId +
                ", containerId=" + containerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", plantName='" + plantName + '\'' +
                ", stage='" + stage + '\'' +
                ", stageStartDate=" + stageStartDate +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
