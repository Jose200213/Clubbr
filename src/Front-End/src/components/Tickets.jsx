import React, {useEffect, useState} from "react";
import {CSSTransition} from "react-transition-group";
import {useAuth} from "../utils/TokenContext";
import FetchApi from "../utils/ApiUtils";
import ticketicon from "../svg/ticketicon.svg";

import "./Tickets/Tickets.css"


function TicketsItem({ ticket }) {

    return (
        <li key={ticket.ticketID} className={`tickets-item`}>
            <div className='tickets-item-container'>
                <div className="tickets-text">
                    <div className='tickets-name'> {ticket.eventName} </div>
                    <div className='tickets-desc'> {ticket.eventDate} </div>
                </div>
                <img className="tickets-icon" alt="Ticket icon" src={ticketicon} />       
            </div>
        </li>
    );
}

function TicketsList() {
    const [tickets, setTickets] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();

    useEffect(() => {

        FetchApi('http://localhost:8080/ticket/all', 'GET', undefined, token)

            .then((ticketsJson) => {
                setTickets(ticketsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    }, []);

    if (tickets === null) {
        return <p>Cargando datos...</p>;
    }

    const ticketsItems = tickets.map((ticket) =>
        <TicketsItem key={ticket.ticketID} ticket={ticket} />);

    return <ul className='tickets-lists'>{ticketsItems}</ul>;
}

export default TicketsList