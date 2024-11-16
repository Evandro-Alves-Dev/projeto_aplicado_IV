
package com.senai.pa4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.senai.pa4.config.CustomLocalDateDeserializer;
import com.senai.pa4.config.CustomLocalDateTimeDeserializer;
import com.senai.pa4.entities.Production;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDTO implements Serializable {

    private Long idProduction;
    private Double planQuantity;
    private Double realQuantity;
    private String unit;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startTime;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime finishTime;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startDowntime = null;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime finishDowntime = null;
    private String packageType;
    private String labelType;
    private String equipment;
    private String workShift;// turno de produção
    private String productionBatch;// lote de produção
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bestBefore;
    private String notes;// observações

    public ProductionDTO(Production entity) {
        idProduction = entity.getIdProduction();
        planQuantity = entity.getPlanQuantity();
        realQuantity = entity.getRealQuantity();
        unit = entity.getUnit();
        startTime = entity.getStartTime();
        finishTime = entity.getFinishTime();
        startDowntime = entity.getStartDowntime();
        finishDowntime = entity.getFinishDowntime(); // tempo de parada
        packageType = entity.getPackageType();
        labelType = entity.getLabelType();
        equipment = entity.getEquipment();
        workShift = entity.getWorkShift(); // turno de trabalho
        productionBatch = entity.getProductionBatch(); // lote de produção
        bestBefore = entity.getBestBefore();
        notes = entity.getNotes(); // observações
    }
}