package com.econtainer.base.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ContainerPlanStage")
public class ContainerPlanStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Container Plan id cannot be null")
    private Long containerPlanId;

    @NotNull
    private String stage;
    private String description;

    @NotNull
    private Integer duration;
    @NotNull
    private Integer startTime;
    @NotNull
    private Integer endTime;

    private Float solutionA;
    private Float solutionB;
    private Float solutionC;

    private Integer ppmMin;
    private Integer ppmMax;

    private Float waterPHMin;
    private Float waterPHMax;
    private Integer waterTempMin;
    private Integer waterTempMax;
    private Float waterECMin;
    private Float waterECMax;

    private Integer airTempMin;
    private Integer airTempMax;
    private Integer airHumidityMin;
    private Integer airHumidityMax;

    private Integer co2Min;
    private Integer co2Max;

    private Integer dissOxygenMin;
    private Integer dissOxygenMax;

    private Timestamp createdOn;
    private Timestamp updatedOn;

    public Long getId() {
        return id;
    }

    public Long getContainerPlanId() {
        return containerPlanId;
    }

    public void setContainerPlanId(Long containerPlanId) {
        this.containerPlanId = containerPlanId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Float getSolutionA() {
        return solutionA;
    }

    public void setSolutionA(Float solutionA) {
        this.solutionA = solutionA;
    }

    public Float getSolutionB() {
        return solutionB;
    }

    public void setSolutionB(Float solutionB) {
        this.solutionB = solutionB;
    }

    public Float getSolutionC() {
        return solutionC;
    }

    public void setSolutionC(Float solutionC) {
        this.solutionC = solutionC;
    }

    public Integer getPpmMin() {
        return ppmMin;
    }

    public void setPpmMin(Integer ppmMin) {
        this.ppmMin = ppmMin;
    }

    public Integer getPpmMax() {
        return ppmMax;
    }

    public void setPpmMax(Integer ppmMax) {
        this.ppmMax = ppmMax;
    }

    public Float getWaterPHMin() {
        return waterPHMin;
    }

    public void setWaterPHMin(Float waterPHMin) {
        this.waterPHMin = waterPHMin;
    }

    public Float getWaterPHMax() {
        return waterPHMax;
    }

    public void setWaterPHMax(Float waterPHMax) {
        this.waterPHMax = waterPHMax;
    }

    public Integer getWaterTempMin() {
        return waterTempMin;
    }

    public void setWaterTempMin(Integer waterTempMin) {
        this.waterTempMin = waterTempMin;
    }

    public Integer getWaterTempMax() {
        return waterTempMax;
    }

    public void setWaterTempMax(Integer waterTempMax) {
        this.waterTempMax = waterTempMax;
    }

    public Float getWaterECMin() {
        return waterECMin;
    }

    public void setWaterECMin(Float waterECMin) {
        this.waterECMin = waterECMin;
    }

    public Float getWaterECMax() {
        return waterECMax;
    }

    public void setWaterECMax(Float waterECMax) {
        this.waterECMax = waterECMax;
    }

    public Integer getAirTempMin() {
        return airTempMin;
    }

    public void setAirTempMin(Integer airTempMin) {
        this.airTempMin = airTempMin;
    }

    public Integer getAirTempMax() {
        return airTempMax;
    }

    public void setAirTempMax(Integer airTempMax) {
        this.airTempMax = airTempMax;
    }

    public Integer getAirHumidityMin() {
        return airHumidityMin;
    }

    public void setAirHumidityMin(Integer airHumidityMin) {
        this.airHumidityMin = airHumidityMin;
    }

    public Integer getAirHumidityMax() {
        return airHumidityMax;
    }

    public void setAirHumidityMax(Integer airHumidityMax) {
        this.airHumidityMax = airHumidityMax;
    }

    public Integer getCo2Min() {
        return co2Min;
    }

    public void setCo2Min(Integer co2Min) {
        this.co2Min = co2Min;
    }

    public Integer getCo2Max() {
        return co2Max;
    }

    public void setCo2Max(Integer co2Max) {
        this.co2Max = co2Max;
    }

    public Integer getDissOxygenMin() {
        return dissOxygenMin;
    }

    public void setDissOxygenMin(Integer dissOxygenMin) {
        this.dissOxygenMin = dissOxygenMin;
    }

    public Integer getDissOxygenMax() {
        return dissOxygenMax;
    }

    public void setDissOxygenMax(Integer dissOxygenMax) {
        this.dissOxygenMax = dissOxygenMax;
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
        ContainerPlanStage that = (ContainerPlanStage) o;
        return id.equals(that.id) &&
                containerPlanId.equals(that.containerPlanId) &&
                stage.equals(that.stage) &&
                Objects.equals(description, that.description) &&
                duration.equals(that.duration) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime) &&
                Objects.equals(solutionA, that.solutionA) &&
                Objects.equals(solutionB, that.solutionB) &&
                Objects.equals(solutionC, that.solutionC) &&
                Objects.equals(ppmMin, that.ppmMin) &&
                Objects.equals(ppmMax, that.ppmMax) &&
                Objects.equals(waterPHMin, that.waterPHMin) &&
                Objects.equals(waterPHMax, that.waterPHMax) &&
                Objects.equals(waterTempMin, that.waterTempMin) &&
                Objects.equals(waterTempMax, that.waterTempMax) &&
                Objects.equals(waterECMin, that.waterECMin) &&
                Objects.equals(waterECMax, that.waterECMax) &&
                Objects.equals(airTempMin, that.airTempMin) &&
                Objects.equals(airTempMax, that.airTempMax) &&
                Objects.equals(airHumidityMin, that.airHumidityMin) &&
                Objects.equals(airHumidityMax, that.airHumidityMax) &&
                Objects.equals(co2Min, that.co2Min) &&
                Objects.equals(co2Max, that.co2Max) &&
                Objects.equals(dissOxygenMin, that.dissOxygenMin) &&
                Objects.equals(dissOxygenMax, that.dissOxygenMax) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, containerPlanId, stage, description, duration, startTime, endTime,
                solutionA, solutionB, solutionC, ppmMin, ppmMax,
                waterPHMin, waterPHMax, waterTempMin, waterTempMax, waterECMin, waterECMax,
                airTempMin, airTempMax, airHumidityMin, airHumidityMax, co2Min, co2Max,
                dissOxygenMin, dissOxygenMax, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return "ContainerPlanStage{" +
                "id=" + id +
                ", containerPlanId=" + containerPlanId +
                ", stage='" + stage + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", solutionA=" + solutionA +
                ", solutionB=" + solutionB +
                ", solutionC=" + solutionC +
                ", ppmMin=" + ppmMin +
                ", ppmMax=" + ppmMax +
                ", waterPHMin=" + waterPHMin +
                ", waterPHMax=" + waterPHMax +
                ", waterTempMin=" + waterTempMin +
                ", waterTempMax=" + waterTempMax +
                ", waterECMin=" + waterECMin +
                ", waterECMax=" + waterECMax +
                ", airTempMin=" + airTempMin +
                ", airTempMax=" + airTempMax +
                ", airHumidityMin=" + airHumidityMin +
                ", airHumidityMax=" + airHumidityMax +
                ", co2Min=" + co2Min +
                ", co2Max=" + co2Max +
                ", dissOxygenMin=" + dissOxygenMin +
                ", dissOxygenMax=" + dissOxygenMax +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
