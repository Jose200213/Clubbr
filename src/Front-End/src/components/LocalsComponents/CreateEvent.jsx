import React, { useState } from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import {EventsFromStabList} from "../Events";
import "./createEvent.css"

const CreateEvent = () => {
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();

    const [loading, setLoading] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');

    const [eventData, setEventData] = useState({
        eventDate: '',
        eventName: '',
        eventDescription: '',
        eventFinishDate: '',
        eventPrice: 0,
        eventTime: ''
    });

    const handleChange = (e) => {
        const {name, value} = e.target;
        setEventData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/event/add`,
                'POST', eventData, token)
            setResponseMessage(response);

        } catch (error) {
            console.error('Error al crear el evento:', error);
            setResponseMessage(`${error.name} - ${error.message}`);

        } finally {
            setLoading(false);
        }
    };

    return (
        <>
        <div className='createEvent-container'>
            <div className = "createEvent-shape"> 
                <h2 className='createEvent-title'>Crear Evento</h2>
                <form className='createEvent-fields-container' onSubmit={handleSubmit}>
                    <input className='createEvent-input'
                        type="text"
                        name="eventName"
                        placeholder='Nombre de Evento'
                        value={eventData.eventName}
                        onChange={handleChange}
                        required
                    />

                    <textarea className='createEvent-desc-input'
                        name="eventDescription"
                        placeholder="Descripcion"
                        value={eventData.eventDescription}
                        onChange={handleChange}
                        required
                    />

                    <label className='createEvent-input-label'>
                        Fecha del Evento:
                        <input className='createEvent-input'
                            type="date"
                            name="eventDate"
                            value={eventData.eventDate}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label className='createEvent-input-label'>
                        Hora del Evento:
                        <input className='createEvent-input'
                            type="time"
                            name="eventTime"
                            value={eventData.eventTime}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label className='createEvent-input-label'>
                        Fecha de finalización del Evento:
                        <input className='createEvent-input'
                            type="date"
                            name="eventFinishDate"
                            value={eventData.eventFinishDate}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label className='createEvent-input-label'>
                        Precio del Evento:
                        <input className='createEvent-input'
                            type="number"
                            name="eventPrice"
                            value={eventData.eventPrice}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <button className='createEvent-submit' type="submit" disabled={loading}>
                        {loading ? 'Cargando...' : 'Crear Evento'}
                    </button>
                </form>
            </div>
        </div>
        <div className='eventsStab-container'>
            {responseMessage && (
                        <p style={{color: responseMessage.includes('Error') ? 'red' : 'green'}}>
                            {responseMessage}
                        </p>
                    )}
            <EventsFromStabList />
        </div>
        
        </>
    );
}
export default CreateEvent