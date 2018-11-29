package com.kopec.wojciech.enginners_thesis.dto;

import org.modelmapper.ModelMapper;

public interface DtoConvertible<T, S> {
    ModelMapper modelMapper = new ModelMapper();

    S toDto(T t);

    T toEntity(S s);
}
