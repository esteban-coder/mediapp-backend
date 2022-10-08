package com.mitocode.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.mitocode.model.Patient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VitalSignDTO {

	@EqualsAndHashCode.Include
	private Integer idVitalSign;
	
	@NotNull
	private Patient patient;
	
	@NotNull
	private LocalDateTime takenDate;
	
	@NotNull
	private String temperature;
	
	@NotNull
	private String pulse;
	
	@NotNull
	private String respiratoryRate;
}
