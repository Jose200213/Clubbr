import React, {useEffect, useState} from "react";
import ticketicon from "../svg/ticketicon.svg";
import {CSSTransition} from "react-transition-group";

import {useAuth} from "../utils/TokenContext";
import FetchApi from "../utils/ApiUtils";
import {Link, useParams} from "react-router-dom";
import eventsicon from "../svg/eventicon.svg";

import "./Events/Events.css"

function EventsItem({event}) {
    const [dropdown, setDropdown] = useState(false);

    return (
        <li key={event.eventID} className={`events-item ${dropdown ? 'push-down' : ''}`}>
            <div className='events-item-container'>
                <div className='events-logo'>
                    <img className='events-image' src={event.logoUrl} alt='logo'/>
                </div>
                
                <div className="events-text">
                    <div className='events-name'> {event.eventName} </div>
                    <div className='events-desc'> {event.eventDescription} </div>
                </div>

                <img className="events-goTo" alt="Tickets icon" src={ticketicon}
                     onClick={() => setDropdown(!dropdown)}
                />
            </div>
            <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
                <div className='events-item-dropdown'>
                    <div className='events-dropdown-container'>
                        <ul className='events-dropdown-list'>
                            <li className='events-dropdown-element'>
                                <Link
                                    to={`/${event.stablishmentID}/${event.eventName}/${event.eventDate}/addTicket`}> Comprar Ticket </Link>
                            </li>

                        </ul>
                    </div>
                </div>
            </CSSTransition>

        </li>
    );
}

function EventsFromStabItem({event}) {
    const [dropdown, setDropdown] = useState(false);


    return (
        <li key={event.eventID} className={`events-item ${dropdown ? 'push-down-eventsStab' : ''}`}>
            <div className='events-item-container'>
                <div className='events-logo'>
                    <img className='events-image' src={event.logoUrl} alt='logo'/>
                </div>

                <div className="events-text">
                    <div className='events-name'> {event.eventName} </div>
                    <div className='events-desc'> {event.eventDescription} </div>
                </div>

                <img className="events-goTo" alt="Tickets icon" src={eventsicon}
                     onClick={() => setDropdown(!dropdown)}
                />

            </div>

            <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
                <div className='events-item-dropdown'>
                    <div className='events-dropdown-container'>
                        <ul className='events-dropdown-list'>
                            <li className='events-dropdown-element'>
                                <Link
                                    to={`/${event.stablishmentID}/${event.eventName}/${event.eventDate}/addWorker`}> Trabajadores </Link>
                            </li>
                            <li className='events-dropdown-element'>
                                <Link
                                    to={`/${event.stablishmentID}/${event.eventName}/${event.eventDate}/addInterestPoint`}> Puntos
                                    de Interes </Link>
                            </li>

                        </ul>
                    </div>
                </div>
            </CSSTransition>
        </li>
    );
}

function EventsList() {
    const [events, setEvents] = useState(null);
    const {getToken} = useAuth();
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
        <EventsItem key={event.eventID} event={event}/>);

    return <ul className='events-lists'>{eventsItems}</ul>;
}

function EventsFromStabList() {
    const [events, setEvents] = useState(null);
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();


    useEffect(() => {

        FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/event/all-ordered`, 'GET', undefined, token)

            .then((eventsJson) => {
                setEvents(eventsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    });

    if (events === null) {
        return <p>Cargando datos...</p>;
    }

    const eventsItems = events.map((event) =>
        <EventsFromStabItem key={event.eventID} event={event}/>);

    return <ul className='eventsStab-lists'>
        {eventsItems}
    </ul>;
}

export {EventsList, EventsFromStabList}