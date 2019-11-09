package fr.volkaert.global_api_metrics.fake_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloRestController {

    Logger logger = LoggerFactory.getLogger(HelloRestController.class);

    @GetMapping(value = "/{name}")
    public String hello(@PathVariable("name") String name, @RequestHeader HttpHeaders headers) {
        logger.info("Hello called with name '{}'", name);
        logger.debug("Headers were {}", headers.toString());
        return "Hello " + name;
    }

    @GetMapping(value = "/4xx")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String respondWith4xxClientError(@RequestHeader HttpHeaders headers) {
        logger.info("Hello called with the special name 4xx to simulate a 4xx client error; return '4xx client error' as body and 400 as status code");
        logger.debug("Headers were {}", headers.toString());
        return "4xx client error";
    }

    @GetMapping(value = "/5xx")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String respondWith5xxServerError(@RequestHeader HttpHeaders headers) {
        logger.info("Hello called with the special name 5xx to simulate a 5xx server error; return '5xx server error' as body and 500 as status code");
        logger.debug("Headers were {}", headers.toString());
        return "5xx server error";
    }
}
