package com.codigo.persistencia.controller;

import com.codigo.persistencia.entity.PedidoEntity;
import com.codigo.persistencia.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/v1")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/crearPedido/{clienteId}")
    public ResponseEntity<List<PedidoEntity>> crearPedidos(@PathVariable Long clienteId,
                                                    @RequestBody List<PedidoEntity> pedidos) {
        List<PedidoEntity> nuevosPedidos = pedidoService.guardarPedidos(clienteId, pedidos);
        return new ResponseEntity<>(nuevosPedidos, HttpStatus.CREATED);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<PedidoEntity>> obtenerTodosLosPedidos(@RequestParam PedidoEntity.Estado estado) {
        return new ResponseEntity<>(pedidoService.obtenerTodosLosPedidos(estado),HttpStatus.OK);
    }

    @GetMapping("/buscarPedidoPorParametro")
    public ResponseEntity<?> obtenerPedidoPorParametro(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "estado", required = false) PedidoEntity.Estado estado) {

        //Validar si el  usuario proporcionó ID
        if (id != null) {
            return new ResponseEntity<>(pedidoService.obtenerPedidoPorId(id),HttpStatus.OK);
        }

        //Validar si el  usuario proporcionó Estado
        if (estado != null) {
            return new ResponseEntity<>(pedidoService.obtenerTodosLosPedidos(estado),HttpStatus.OK);
        }

        //Usuario no envió ningún parámetro
        return new ResponseEntity<>("No envio parametro", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/actualizarPedido/{id}/cliente/{idCLiente}")
    public ResponseEntity<PedidoEntity> actualizarPedido(@PathVariable Long id,
                                                         @PathVariable Long idCLiente,
                                                         @RequestBody PedidoEntity pedido) {
        PedidoEntity pedidoActualizado = pedidoService.actualizarPedido(id,idCLiente ,pedido);
        return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarPedido/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
