package es.soprasteria.formacion.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SkillDto {
  private String nif;
  private List<String> skills;
}
