package org.example.tutorial.feign;

import org.example.tutorial.dto.PriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "price",url = "http://localhost:8080")
public interface Price {

    @GetMapping("/price/{id}")
    Optional<PriceDto> getPrice(@PathVariable("id") Long id);

}
