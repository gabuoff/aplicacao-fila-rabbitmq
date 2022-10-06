package com.microservico.estoquepreco.controller;

import com.microservico.estoquepreco.dto.InventoryDTO;
import com.microservico.estoquepreco.services.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity changeInventory(@RequestBody InventoryDTO inventoryDTO){
        this.rabbitMQService.sendMessage(RabbitMQConstants.INVENTORY_QUEUE, inventoryDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

}
