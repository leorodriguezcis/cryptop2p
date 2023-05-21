package com.spring.universidad.cryptop2p.modelo.entities.dto;

public class DolarDTO {
    private String compra;
    private String venta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String nombre;


    public void setCompra(String compra) {
        this.compra = compra;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getCompra() {
        return compra;
    }

    public String getVenta() {
        return venta;
    }
}
