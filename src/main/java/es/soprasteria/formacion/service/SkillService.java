package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.SkillDto;

public interface SkillService {
  SkillDto getSkill(String nif);

  Boolean createSkill(SkillDto newSkill);
}
