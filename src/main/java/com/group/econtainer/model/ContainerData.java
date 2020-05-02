package com.group.econtainer.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ContainerData")
public class ContainerData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @NotNull(message = "Container id cannot be null")
  private Long containerId;

  private Integer airTemp;

  private Integer airHumidity;

  private Integer airCo2;

  private Boolean airVentilation;

  private Integer waterPh;

  private Double waterEc;

  private Boolean lightGrow;

  private Boolean lightSeed;

  private Boolean lightWork;

  @NotNull
  private Timestamp internalTime;

  @NotNull
  private Timestamp globalTime;

  @Override
  public String toString() {
    return "ContainerData{" +
        "containerId=" + containerId +
        ", airTemp=" + airTemp +
        ", airHumidity=" + airHumidity +
        ", airCo2=" + airCo2 +
        ", airVentilation=" + airVentilation +
        ", waterPh=" + waterPh +
        ", waterEc=" + waterEc +
        ", lightGrow=" + lightGrow +
        ", lightSeed=" + lightSeed +
        ", lightWork=" + lightWork +
        ", internalTime=" + internalTime +
        ", globalTime=" + globalTime +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContainerData that = (ContainerData) o;
    return containerId.equals(that.containerId) &&
        Objects.equals(airTemp, that.airTemp) &&
        Objects.equals(airHumidity, that.airHumidity) &&
        Objects.equals(airCo2, that.airCo2) &&
        Objects.equals(airVentilation, that.airVentilation) &&
        Objects.equals(waterPh, that.waterPh) &&
        Objects.equals(waterEc, that.waterEc) &&
        Objects.equals(lightGrow, that.lightGrow) &&
        Objects.equals(lightSeed, that.lightSeed) &&
        Objects.equals(lightWork, that.lightWork) &&
        internalTime.equals(that.internalTime) &&
        globalTime.equals(that.globalTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(containerId, airTemp, airHumidity, airCo2, airVentilation, waterPh, waterEc,
        lightGrow, lightSeed, lightWork, internalTime, globalTime);
  }

  public Long getContainerId() {
    return containerId;
  }

  public void setContainerId(Long containerId) {
    this.containerId = containerId;
  }

  public Integer getAirTemp() {
    return airTemp;
  }

  public void setAirTemp(Integer airTemp) {
    this.airTemp = airTemp;
  }

  public Integer getAirHumidity() {
    return airHumidity;
  }

  public void setAirHumidity(Integer airHumidity) {
    this.airHumidity = airHumidity;
  }

  public Integer getAirCo2() {
    return airCo2;
  }

  public void setAirCo2(Integer airCo2) {
    this.airCo2 = airCo2;
  }

  public Boolean getAirVentilation() {
    return airVentilation;
  }

  public void setAirVentilation(Boolean airVentilation) {
    this.airVentilation = airVentilation;
  }

  public Integer getWaterPh() {
    return waterPh;
  }

  public void setWaterPh(Integer waterPh) {
    this.waterPh = waterPh;
  }

  public Double getWaterEc() {
    return waterEc;
  }

  public void setWaterEc(Double waterEc) {
    this.waterEc = waterEc;
  }

  public Boolean getLightGrow() {
    return lightGrow;
  }

  public void setLightGrow(Boolean lightGrow) {
    this.lightGrow = lightGrow;
  }

  public Boolean getLightSeed() {
    return lightSeed;
  }

  public void setLightSeed(Boolean lightSeed) {
    this.lightSeed = lightSeed;
  }

  public Boolean getLightWork() {
    return lightWork;
  }

  public void setLightWork(Boolean lightWork) {
    this.lightWork = lightWork;
  }

  public Timestamp getInternalTime() {
    return internalTime;
  }

  public void setInternalTime(Timestamp internalTime) {
    this.internalTime = internalTime;
  }

  public Timestamp getGlobalTime() {
    return globalTime;
  }

  public void setGlobalTime(Timestamp globalTime) {
    this.globalTime = globalTime;
  }
}
