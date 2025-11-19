package com.devops.agendamento.model;

public enum RequestStatus {
    PENDENT("pendente"),
    ACCEPTED("aceito"),
    REFUSED("recusado"),
    CANCELED("cancelado");

    private final String statusBr;

    RequestStatus(String statusBr) {
        this.statusBr = statusBr;
    }

    public String getStatusBr() {
        return statusBr;
    }
}
