import React, {useState} from "react";
import eventsicon from "../../svg/eventicon.svg";
import {CSSTransition} from "react-transition-group";
import {tickets} from "../../tickets";

function TicketsItem({ ticket }) {
    const [dropdown, setDropdown] = useState(false);

    return (
        <li key={ticket.id} className={`page-lists-item ${dropdown ? 'push-down' : ''}`}>
            <div className='page-lists-item-container'>
                <img className="page-lists-item-container-event" alt="Event icon" src={eventsicon} onClick={() => setDropdown(!dropdown)}/>
                <div className='page-lists-item-container-name'> {ticket.name} </div>
                <div className='page-lists-item-container-desc'> {ticket.schedule} </div>
            </div>
            <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
                <div className='page-lists-item-dropdown'>
                    <div className='page-lists-item-dropdown-tickets'>
                        <ul className='dropdown-tickets-list'>
                            <li className='dropdown-tickets-list-element'>item1</li>
                            <li className='dropdown-tickets-list-element'>item2</li>
                            <li className='dropdown-tickets-list-element'>item3</li>
                        </ul>
                    </div>

                </div>
            </CSSTransition>
        </li>
    );
}

function TicketsList() {
    const ticketsItems = tickets.map((ticket) => <TicketsItem key={ticket.id} ticket={ticket} />);

    return <ul className='page-lists'>{ticketsItems}</ul>;
}

export default TicketsList