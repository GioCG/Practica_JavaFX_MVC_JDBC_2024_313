
package org.giovannicarrera.modelo;

/**
 *
 * @author Dell G7
 */
public class Cargos {
    private int cargoId;
    private String nombreCargo;
    private String descripcionCargo;

    public Cargos() {
        
    }

    public Cargos(int cargoId, String nombreCargo, String descripcionCargo) {
        this.cargoId = cargoId;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    @Override
    public String toString() {
        return "Cargos{" + "cargoId=" + cargoId + ", nombreCargo=" + nombreCargo + ", descripcionCargo=" + descripcionCargo + '}';
    }
    
    
}
