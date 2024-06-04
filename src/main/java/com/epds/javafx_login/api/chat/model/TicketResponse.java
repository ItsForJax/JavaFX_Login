package com.epds.javafx_login.api.chat.model;

import java.util.List;

public class TicketResponse {
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
