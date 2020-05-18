package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.PersonDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PersonServiceImpl implements PersonService {

  @Value("${es.soprasteria.person.url}")
  private String baseResource;

  private RestTemplate restTemplate;

  public PersonServiceImpl() {
    this.restTemplate = new RestTemplate();
  }

  @Override
  public PersonDto getByNif(String nif) {
    ResponseEntity<PersonDto> personResponse = this.restTemplate.getForEntity(baseResource + nif, PersonDto.class);
    return personResponse.getBody();
  }
}
