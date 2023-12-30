import React, {useEffect, useState} from "react";
import ticketicon from "../../svg/ticketicon.svg";
import {CSSTransition} from "react-transition-group";

import {useAuth} from "../../utils/TokenContext";
import FetchApi from "../../utils/ApiUtils";

import "./Events.css"

function EventsItem({ event }) {
    const [dropdown, setDropdown] = useState(false);

    return (
        <li key={event.eventName} className={`page-lists-item ${dropdown ? 'push-down' : ''}`}>
            <div className='page-lists-item-container'>
                <div className='page-lists-item-container-ellipse'>
                    <img className='page-lists-item-container-logo' src={event.logoUrl} alt='logo' />
                </div>
                <img className="page-lists-item-container-event" alt="Event icon" src={ticketicon} onClick={() => setDropdown(!dropdown)}
                />
                <div className='page-lists-item-container-name'> {event.eventName} </div>
                <div className='page-lists-item-container-desc'> {event.eventDescription} </div>
            </div>
            <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
                <div className='page-lists-item-dropdown'>
                    <div className='page-lists-item-dropdown-events'>
                        <ul className='dropdown-events-list'>
                            <li className='dropdown-events-list-element'>item1</li>
                            <li className='dropdown-events-list-element'>item2</li>
                            <li className='dropdown-events-list-element'>item3</li>
                        </ul>
                    </div>
                </div>
            </CSSTransition>
        </li>
    );
}

function EventsList() {
    const [events, setEvents] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();


    useEffect(() => {

        FetchApi('http://localhost:8080/event/all', 'GET', undefined, token)

            .then((eventsJson) => {
                setEvents(eventsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    }, []);

    if (events === null) {
        return <p>Cargando datos...</p>;
    }

    const eventsItems = events.map((event) =>
        <EventsItem key={event.eventName} event={event} />);

    return <ul className='page-lists'>{eventsItems}</ul>;
}

export default EventsList