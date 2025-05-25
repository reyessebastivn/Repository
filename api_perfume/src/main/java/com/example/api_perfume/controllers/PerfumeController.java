package com.example.api_perfume.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api_perfume.models.ModificarPerfume;
import com.example.api_perfume.models.Perfume;
import com.example.api_perfume.services.PerfumeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    @Autowired
    private PerfumeService sPerfume;

    @GetMapping("")
    public List<Perfume> todos() {
        return sPerfume.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Perfume obtenerUno(@PathVariable long id) {
        System.out.println(">>>>Id buscando:" + id);
        return sPerfume.obtenerUno(id);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable long id) {
        sPerfume.eliminar(id);
        return "Eliminado!";
    }

    @PostMapping("")
    public String agregar(@Valid @RequestBody Perfume perfume) {
        sPerfume.agregar(perfume);
        return "Agregado!";
    }

    @PutMapping("/{id}")
    public String modificarPrecio(@PathVariable long id, @RequestBody ModificarPerfume perfume) {
        sPerfume.modificar(id, perfume);
        return "Modificado!";
    }

    // ✅ Ruta para actualizar solo el stock del perfume
    @PutMapping("/{id}/stock")
    public ResponseEntity<?> actualizarStock(@PathVariable Long id, @RequestBody Perfume perfumeActualizado) {
        Perfume existente = sPerfume.obtenerUno(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setStock(perfumeActualizado.getStock());
        sPerfume.agregar(existente); // reutiliza método que guarda
        return ResponseEntity.ok(existente);
    }
}
