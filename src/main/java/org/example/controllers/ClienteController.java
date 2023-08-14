package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ClienteDTO;
import org.example.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/getAll")
    @Operation(summary = "Hola")
    public List<ClienteDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "1") int size) {
        List<ClienteDTO> listCliente = clienteService.getAll(page, size);
        return listCliente;
    }

    @PostMapping()
    public ResponseEntity<?> createCliente(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }
        ClienteDTO createdCliente = clienteService.createCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("El cliente fue creado exitosamente");
    }

    @PatchMapping("/{clienteId}")
    public ResponseEntity<?> updateCliente(@PathVariable Long clienteId, @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO updatedCliente = clienteService.updateCliente(clienteId, clienteDTO);
            if (updatedCliente != null) {
                return ResponseEntity.ok("La actualización se realizó exitosamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> delete(@PathVariable Long clienteId) {
        clienteService.delete(clienteId);
        return ResponseEntity.noContent().build();
    }
}
