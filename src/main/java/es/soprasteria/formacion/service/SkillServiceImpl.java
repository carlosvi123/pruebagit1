package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.SkillDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SkillServiceImpl implements SkillService {
  @Value("${es.soprasteria.skill.url}")
  private String baseResource;

  private RestTemplate restTemplate;

  public SkillServiceImpl() {
    this.restTemplate = new RestTemplate();
  }

  @Override
  public SkillDto getSkill(String nif) {
    ResponseEntity<SkillDto> response = restTemplate.getForEntity(baseResource + nif, SkillDto.class);
    return response.getBody();
  }
}
