package com.microservico.estoquepreco.controller;

import com.microservico.estoquepreco.services.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dto;
@RestController
@RequestMapping(value = "/price")
public class PriceController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity changePrice(@RequestBody PriceDTO priceDTO){
        this.rabbitMQService.sendMessage(RabbitMQConstants.PRICE_QUEUE, priceDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

}
