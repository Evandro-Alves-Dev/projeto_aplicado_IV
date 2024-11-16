
package com.senai.pa4.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.senai.pa4.config.CustomLocalDateDeserializer;
import com.senai.pa4.config.CustomLocalDateTimeDeserializer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_production")
public class Production implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDateTime startDowntime = null ;
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime finishDowntime = null;
    private String downtime;
    private String packageType;
    private String labelType;
    private String equipment;
    private String workShift;   // turno de produção
    private String productionBatch; // lote de produção
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bestBefore;  // validade do produto
    private String notes;   // observações
}
