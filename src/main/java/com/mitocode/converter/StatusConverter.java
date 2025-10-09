package com.mitocode.converter;

import com.mitocode.model.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status == null ? null : status.getDbValue();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return Status.fromDbValue(dbData);
    }
}
