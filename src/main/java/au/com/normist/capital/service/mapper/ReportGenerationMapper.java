package au.com.normist.capital.service.mapper;

import au.com.normist.capital.domain.*;
import au.com.normist.capital.service.dto.ReportGenerationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ReportGeneration and its DTO ReportGenerationDTO.
 */
@Mapper(componentModel = "spring", uses = {ReportMapper.class})
public interface ReportGenerationMapper extends EntityMapper<ReportGenerationDTO, ReportGeneration> {

    @Mapping(source = "report.id", target = "reportId")
    ReportGenerationDTO toDto(ReportGeneration reportGeneration);

    @Mapping(source = "reportId", target = "report")
    ReportGeneration toEntity(ReportGenerationDTO reportGenerationDTO);

    default ReportGeneration fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportGeneration reportGeneration = new ReportGeneration();
        reportGeneration.setId(id);
        return reportGeneration;
    }
}
