package es.soprasteria.formacion.service;

import es.soprasteria.formacion.dto.ProfileDto;
import java.util.List;

public interface ProfileService {

  ProfileDto getProfile(String nif);
}
