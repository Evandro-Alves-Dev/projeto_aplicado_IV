
package com.senai.pa4.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.senai.pa4.dto.ProductionDTO;
import com.senai.pa4.entities.Production;
import com.senai.pa4.enums.WorkShiftEnum;
import com.senai.pa4.exceptions.ResourceNotFoundException;
import com.senai.pa4.repository.ProductionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;

    public ProductionService(ProductionRepository productionRepository) {
        this.productionRepository = productionRepository;
    }

    public ByteArrayInputStream findAll2() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph paragraph = new Paragraph("Relatório de Produção");
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(10);
            paragraph.setSpacingBefore(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(16);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            String[] headers = {"Id", "Quantidade Planejada", "Quantidade Real", "Unidade", "Hora de Início", "Hora de Término", "Início da Parada", "Fim da Parada", "Tempo Total da Parada", "Tipo de Embalagem", "Tipo de Rótulo", "Equipamento", "Turno de Trabalho", "Lote de Produção", "Data de Validade", "Observações"};

            for (String header : headers) {
                PdfPCell hcell = new PdfPCell(new Phrase(header, headFont));
                hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(hcell);
            }

            List<Production> productions = productionRepository.findAll();

            productions.stream()
                    .sorted(Comparator.comparing(Production::getIdProduction))
                    .forEach(production -> {
                ProductionDTO productionDTO = new ProductionDTO(production);
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(productionDTO.getIdProduction().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getPlanQuantity().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getRealQuantity().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getUnit()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getStartTime().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getFinishTime().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getStartDowntime() == null ? "" : productionDTO.getStartDowntime().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getFinishDowntime() == null ? "" : productionDTO.getFinishDowntime().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(production.getDowntime() == null ? "" : production.getDowntime().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getPackageType()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getLabelType()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getEquipment()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getWorkShift()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getProductionBatch()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getBestBefore().toString()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(productionDTO.getNotes() == null ? "" : productionDTO.getNotes()));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            });

            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Transactional(readOnly = true)
    public List<ProductionDTO> findAll() {
        List<Production> productions = productionRepository.findAll();
        return productions.stream()
                .sorted(Comparator.comparing(Production::getIdProduction))
                .map(ProductionDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductionDTO findById(Long id) {
        Production production = productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " não encontrado"));
        return new ProductionDTO(production);
    }

    @Transactional
    public ProductionDTO insert(ProductionDTO productionDTO) {
        Production production = new Production();
        copyDtoToEntity(productionDTO, production);
        production = productionRepository.save(production);
        return new ProductionDTO(production);
    }

    @Transactional
    public ProductionDTO update(Long id, ProductionDTO productionDTO) {
        try {
            var entity = productionRepository.getOne(id);
            copyDtoToEntityUpdate(productionDTO, entity);
            entity = productionRepository.save(entity);
            return new ProductionDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!productionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id " + id + " não encontrado");
        }
        productionRepository.deleteById(id);
    }

    private void copyDtoToEntity(ProductionDTO productionDTO, Production production) {
        production.setPlanQuantity(productionDTO.getPlanQuantity());
        production.setRealQuantity(productionDTO.getRealQuantity());
        production.setUnit(productionDTO.getUnit());
        production.setStartTime(buildFormartDate(productionDTO.getStartTime()));
        production.setFinishTime(buildFormartDateFinish(productionDTO.getFinishTime(), null));
        production.setStartDowntime(productionDTO.getStartDowntime());
        production.setFinishDowntime(productionDTO.getFinishDowntime());
        // Tempo de parada definido automaticamente
        production.setDowntime(buildDowntime(productionDTO.getStartDowntime(), productionDTO.getFinishDowntime(), null, "INSERT"));
        production.setPackageType(productionDTO.getPackageType());
        production.setLabelType(productionDTO.getLabelType());
        production.setEquipment(productionDTO.getEquipment());
        // Turno de trabalho definido automaticamente
        production.setWorkShift(buildWorkShift(productionDTO.getWorkShift()));
        // Lote de produção definido automaticamente
        production.setProductionBatch(buildBatch(production.getWorkShift()));
        production.setBestBefore(validatorBestBefore(productionDTO.getBestBefore(), production.getBestBefore(), "INSERT"));
        production.setNotes(productionDTO.getNotes());
    }

    private void copyDtoToEntityUpdate(ProductionDTO productionDTO, Production entity) {
        entity.setPlanQuantity(productionDTO.getPlanQuantity());
        entity.setRealQuantity(productionDTO.getRealQuantity());
        entity.setUnit(productionDTO.getUnit());
        entity.setFinishTime(buildFormartDateFinish(productionDTO.getFinishTime(), Optional.of(entity)));
        entity.setStartDowntime(productionDTO.getStartDowntime());
        entity.setFinishDowntime(productionDTO.getFinishDowntime());
        // Tempo de parada definido automaticamente
        entity.setDowntime(buildDowntime(productionDTO.getStartDowntime(), productionDTO.getFinishDowntime(), Optional.of(entity), "UPDATE"));
        entity.setPackageType(productionDTO.getPackageType());
        entity.setLabelType(productionDTO.getLabelType());
        entity.setBestBefore(validatorBestBefore(productionDTO.getBestBefore(), entity.getBestBefore(), "UPDATE"));
        entity.setNotes(productionDTO.getNotes());
    }

    private String buildWorkShift(String workShift) {
        if (workShift == null || workShift.isEmpty()) {
            return WorkShiftEnum.parseToString(LocalDateTime.now());
        } else {
            return WorkShiftEnum.parse(workShift.toUpperCase());
        }
    }

    private String buildBatch(String workShift) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(inputFormatter), inputFormatter);
        String output = dateTime.format(outputFormatter);

        var batch = "LT" + " - " + output + " - " + workShift;
        return batch;
    }

    private LocalDateTime buildFormartDate(LocalDateTime startTime) {
        if (startTime == null) {
            return LocalDateTime.now();
        } else {
            return startTime;
        }
    }

    private LocalDateTime buildFormartDateFinish(LocalDateTime finishTime, Optional<Production> entity) {
        if (finishTime == null || finishTime.toString().isBlank()) {
            return entity.get().getFinishTime();
        } else {
            return finishTime;
        }
    }

    private String buildDowntime(LocalDateTime startDowntime, LocalDateTime finishDowntime, Optional<Production> entity, String method) {
        if ((startDowntime == null && finishDowntime != null) || (startDowntime != null && finishDowntime == null)) {
            throw new ResourceNotFoundException("Data de início e fim de parada devem ser preenchidas juntas");
        }

        if (startDowntime == null && finishDowntime == null) {
            if (method.equals("INSERT")) {
                return null;
            } else if (method.equals("UPDATE")) {
                return entity.get().getDowntime();
            }

        }

        Duration duration = Duration.between(startDowntime, finishDowntime);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        LocalTime downtime = LocalTime.of((int) hours, (int) minutes, (int) seconds);

        return downtime.toString();
    }

    private LocalDate validatorBestBefore(LocalDate bestBefore, LocalDate entity, String method) {
        if (bestBefore == null) {

            if (method.equals("INSERT")) {
                throw new ResourceNotFoundException("Data de validade não pode ser nula");
            } else {
                return entity;
            }
        } else if (bestBefore.isBefore(LocalDate.now())) {
            throw new ResourceNotFoundException("Data de validade não pode ser menor que a data atual");
        }
        return bestBefore;
    }
}
