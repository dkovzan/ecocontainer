package com.group.ecocontainer.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "weather")
public class Weather {

	public Weather() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Container id cannot be null")
	private Long container_id;

	private Integer temperature;

	private Integer humidity;

	private Integer pressure;

	@NotNull
	private LocalDateTime created_on;

	@NotNull
	private LocalDateTime internal_time;

	@Override
	public String toString() {
		return "InternalWeather{" +
				"id=" + id +
				", container_id=" + container_id +
				", temperature=" + temperature +
				", humidity=" + humidity +
				", pressure=" + pressure +
				", created_on=" + created_on +
				", internal_time=" + internal_time +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Weather that = (Weather) o;
		return id.equals(that.id) &&
				container_id.equals(that.container_id) &&
				Objects.equals(temperature, that.temperature) &&
				Objects.equals(humidity, that.humidity) &&
				Objects.equals(pressure, that.pressure) &&
				created_on.equals(that.created_on) &&
				internal_time.equals(that.internal_time);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, container_id, temperature, humidity, pressure, created_on, internal_time);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContainer_id() {
		return container_id;
	}

	public void setContainer_id(Long container_id) {
		this.container_id = container_id;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Integer getPressure() {
		return pressure;
	}

	public void setPressure(Integer pressure) {
		this.pressure = pressure;
	}

	public LocalDateTime getCreated_on() {
		return created_on;
	}

	public void setCreated_on(LocalDateTime created_on) {
		this.created_on = created_on;
	}

	public LocalDateTime getInternal_time() {
		return internal_time;
	}

	public void setInternal_time(LocalDateTime internal_time) {
		this.internal_time = internal_time;
	}
}
