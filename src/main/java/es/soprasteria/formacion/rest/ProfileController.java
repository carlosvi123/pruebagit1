package es.soprasteria.formacion.rest;

import es.soprasteria.formacion.dto.ProfileDto;
import es.soprasteria.formacion.service.ProfileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/profile")
public class ProfileController {
  @Autowired
  private ProfileService profileService;

  @ApiOperation(value="Recupera un perfil en función de su NIF")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Perfil recuperada correctamente"),
      @ApiResponse(code = 404, message = "Perfil no existe en base de datos"),
  })
  @GetMapping(value = "/{nif}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  public ResponseEntity<ProfileDto> findProfile(@PathVariable(name="nif") String nif) {
    ProfileDto profile = profileService.getProfile(nif);
    if (profile == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(profile);
    }
  }

}
