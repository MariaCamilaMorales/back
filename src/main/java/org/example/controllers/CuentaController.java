package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.CuentaDTO;
import org.example.services.CuentaService;
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
@RequestMapping("/v1/cuenta")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/getAll")
    public List<CuentaDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "1") int size) {
        List<CuentaDTO> listCuenta = cuentaService.getAll(page, size);
        return listCuenta;
    }
    @PostMapping()
    public ResponseEntity<?> createCuenta(@Valid @RequestBody CuentaDTO cuentaDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }
        CuentaDTO createdCuenta = cuentaService.createCuenta(cuentaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCuenta);
    }

    @PatchMapping("/{cuentaId}")
    public ResponseEntity<?> updateCuenta(@PathVariable Long cuentaId, @RequestBody CuentaDTO cuentaDTO) {
        try {
            CuentaDTO updatedCuenta = cuentaService.updateCuenta(cuentaId, cuentaDTO);
            if (updatedCuenta != null) {
                return ResponseEntity.ok(updatedCuenta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cuentaId}")
    public ResponseEntity<Void> delete(@PathVariable Long cuentaId) {
        cuentaService.delete(cuentaId);
        return ResponseEntity.noContent().build();
    }
}
