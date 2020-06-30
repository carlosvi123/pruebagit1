package es.soprasteria.formacion.rest;

import es.soprasteria.formacion.dto.PersonDto;
import es.soprasteria.formacion.dto.ProfileDto;
import es.soprasteria.formacion.dto.SkillDto;
import es.soprasteria.formacion.service.ProfileService;
import es.soprasteria.formacion.service.SkillService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/profile")
public class ProfileController {
  private ProfileService profileService;
  private SkillService skillService;
  private MeterRegistry registry;
  private CollectorRegistry collectorRegistry;
  private Counter numRequest;
  private Gauge gauge;
  private Histogram histogram;

  @Autowired
  public ProfileController(ProfileService profileService, SkillService skillService, MeterRegistry registry, CollectorRegistry collectorRegistry) {
    this.profileService = profileService;
    this.skillService = skillService;
    this.registry = registry;
    this.collectorRegistry = collectorRegistry;
    this.numRequest = Counter.builder("practica3_nif_endpoint_num_request").description("Numero de peticiones").register(registry);
    this.gauge = Gauge.build().name("practica3_nif_endpoint_concurrent_requests").help("Numero de peticiones concurrentes").register(collectorRegistry);
    this.histogram = Histogram.build().name("practica3_nif_endpoint_execution_time").help("Tiempo de ejecucion").register(collectorRegistry);
  }

  @ApiOperation(value="Recupera un perfil en función de su NIF")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Perfil recuperada correctamente"),
      @ApiResponse(code = 404, message = "Perfil no existe en base de datos"),
  })
  @GetMapping(value = "/{nif}", produces = {
      MediaType.APPLICATION_JSON_VALUE,
      MediaType.APPLICATION_XML_VALUE
  })
  public ResponseEntity<ProfileDto> findProfile(@PathVariable(name="nif") String nif) throws InterruptedException {
    log.info("Find profile by nif '{}", nif);
    Histogram.Timer histogramTimer = this.histogram.startTimer();
    this.numRequest.increment();

    this.registry.counter("practica3_nif_endpoint_num_request_by_nif", "NIF", nif).increment();
    this.gauge.inc();
    ProfileDto profile = profileService.getProfile(nif);
    this.gauge.dec();
    histogramTimer.observeDuration();

    if (profile == null) {
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(profile);
    }
  }

  @PostMapping("/skill")
  public ResponseEntity<Boolean> createSkill(@RequestBody SkillDto newSkill) {
    Boolean result = skillService.createSkill(newSkill);
    if (result) {
      return ResponseEntity.ok(true);
    } else {
      return ResponseEntity.badRequest().body(false);
    }
  }

}
