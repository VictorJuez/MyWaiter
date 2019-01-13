package com.example.victorjuez.mywaiter.Controller;

import com.example.victorjuez.mywaiter.Model.Plate;

import java.util.ArrayList;

public class PlateController {
    private static final PlateController ourInstance = new PlateController();
    private Plate selectedPlate;

    public static PlateController getInstance() {
        return ourInstance;
    }

    public Plate getSelectedPlate() {
        return selectedPlate;
    }

    public void setSelectedPlate(Plate selectedPlate) {
        this.selectedPlate = selectedPlate;
    }

    private PlateController() {
    }
}
