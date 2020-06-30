package es.soprasteria.formacion.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import es.soprasteria.formacion.dto.PersonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PersonServiceImpl implements PersonService {

  @Value("${es.soprasteria.person.url}")
  private String baseResource;

  private RestTemplate restTemplate;

  @Autowired
  public PersonServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @HystrixCommand(fallbackMethod = "fallbackNif", commandProperties = {
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
  })
  @Override
  public PersonDto getByNif(String nif) {
    ResponseEntity<PersonDto> personResponse = this.restTemplate.getForEntity(baseResource + nif, PersonDto.class);
    return personResponse.getBody();
  }

  private PersonDto fallbackNif(String nif) {
    PersonDto personDto = new PersonDto();
    personDto.setFullName("Not available");
    personDto.setAge(-1);
    personDto.setNif(nif);
    return personDto;
  }
}
