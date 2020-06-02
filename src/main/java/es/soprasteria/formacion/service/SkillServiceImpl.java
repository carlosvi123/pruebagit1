package es.soprasteria.formacion.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import es.soprasteria.formacion.dto.SkillDto;
import java.util.ArrayList;
import java.util.Arrays;
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

  @HystrixCommand(fallbackMethod = "fallbackSkill", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })
  @Override
  public SkillDto getSkill(String nif) {
    ResponseEntity<SkillDto> response = restTemplate.getForEntity(baseResource + nif, SkillDto.class);
    return response.getBody();
  }

  private SkillDto fallbackSkill(String nif) {
    SkillDto skillDto = new SkillDto();
    skillDto.setNif(nif);
    skillDto.setSkills(Arrays.asList("No skills available."));
    return skillDto;
  }
}
