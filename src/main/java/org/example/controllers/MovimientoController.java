package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.CuentaDTO;
import org.example.dto.MovimientoDTO;
import org.example.exception.CustomException;
import org.example.services.MovimientoService;
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
@RequestMapping("/v1/movimiento")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/getAll")
    public List<MovimientoDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "1") int size) {
        List<MovimientoDTO> listMovimiento = movimientoService.getAll(page, size);
        return listMovimiento;
    }
    @PostMapping()
    public ResponseEntity<?> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            MovimientoDTO createdMovimiento = movimientoService.createMovimiento(movimientoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("El movimiento fue creado exitosamente");
        }
        catch (CustomException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PatchMapping("/{movimientoId}")
    public ResponseEntity<?> updateMovimiento(@PathVariable Long movimientoId, @RequestBody MovimientoDTO movimientoDTO) {
        try {
            MovimientoDTO updatedMovimiento = movimientoService.updateMovimiento(movimientoId, movimientoDTO);
            if (updatedMovimiento != null) {
                return ResponseEntity.ok("La actualización se realizó exitosamente");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{movimientoId}")
    public ResponseEntity<Void> delete(@PathVariable Long movimientoId) {
        movimientoService.delete(movimientoId);
        return ResponseEntity.noContent().build();
    }
}
