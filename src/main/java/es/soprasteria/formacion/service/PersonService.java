package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.PersonDto;

public interface PersonService {
  PersonDto getByNif(String nif);
}
