package com.appnutricare.service;

import javassist.bytecode.Opcode;

import java.util.List;
import java.util.Optional;

public interface CrudService<T>{
    T save(T t) throws Exception;   //actualizar o crear
    void delete(Integer id) throws Exception;
    List<T> getAll() throws Exception;
    Optional<T> getById(Integer id) throws Exception;
}
