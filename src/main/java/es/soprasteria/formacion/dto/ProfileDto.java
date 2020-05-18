package es.soprasteria.formacion.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileDto {
  private String nif;
  private String fullName;
  private Integer age;
  private List<String> skills;
}
