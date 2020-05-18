package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.PersonDto;
import es.soprasteria.formacion.dto.ProfileDto;
import es.soprasteria.formacion.dto.SkillDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProfileServiceImpl implements ProfileService {

  @Autowired
  private PersonService personService;

  @Autowired
  private SkillService skillService;

  @Override
  public ProfileDto getProfile(String nif) {
    PersonDto personDto = personService.getByNif(nif);
    SkillDto skillDto = skillService.getSkill(nif);

    ProfileDto profileDto = ProfileDto.builder()
        .fullName(personDto.getFullName())
        .nif(personDto.getNif())
        .age(personDto.getAge())
        .skills(skillDto.getSkills())
        .build();
    return profileDto;
  }
}
