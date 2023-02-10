package ru.senya.gateway.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;
import ru.senya.gateway.entity.enums.EmploymentPosition;
import ru.senya.gateway.entity.enums.EmploymentStatus;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmploymentDTO {

    @NotEmpty
    @Schema(description = "Рабочий статус: SELFEMPLOYED | UNEMPLOYED | BUSINESSOWNER")
    private EmploymentStatus status;

    @NotEmpty
    @Size(min = 9, max = 9)
    @Schema(description = "ИНН работника")
    private String employerINN;

    @NumberFormat
    @Schema(description = "Зарплата")
    private BigDecimal salary;

    @NotEmpty
    @Schema(description = "Позиция на работе: MIDDLEMANAGER, TOPMANAGER")
    private EmploymentPosition position;

    @NumberFormat
    @Schema(description = "Общий стаж работы")
    private Integer workExperienceTotal;

    @NumberFormat
    @Schema(description = "Настоящий стаж работы")
    private Integer workExperienceCurrent;


}
