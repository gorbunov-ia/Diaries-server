package ru.gorbunov.diaries.converters;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 * Common shared configuration.
 */
@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR, componentModel = "spring")
public interface DefaultConfig {
}
