import React, {useEffect, useState} from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import {InterestPointItem} from "../LocalsComponents/InterestPointList";
import "./AddInterestPointToEvent.css"


const AddInterestPointToEvent = () => {
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();
    const {eventName} = useParams();
    const {eventDate} = useParams();

    const [loading, setLoading] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');

    const [interestPointData, setInterestPointData] = useState({
        stablishmentID: {stablishmentID: stablishmentID},
        description: '',
        xCoordinate: 0,
        yCoordinate: 0,
        eventID: {eventName: `${eventName}`, eventDate: `${eventDate}`},

    });

    const handleChange = (e) => {
        const {name, value} = e.target;
        setInterestPointData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            console.log(interestPointData)
            const response = await FetchApi(
                `http://localhost:8080/stablishment/${stablishmentID}/event/${eventName}/${eventDate}/interestPoint/add`,
                'POST', interestPointData, token)
            setResponseMessage(response);

        } catch (error) {
            console.error('Error al crear el trabajador:', error);

            setResponseMessage(`${error.name} - ${error.message}`);
            console.log(responseMessage)

        } finally {
            setLoading(false);
        }
    };

    return (
        <>
        <div className = "addInterestPointToEvent-container">
          <div className = "addInterestPointToEvent-shape">
            <h2 className='addInterestPointToEvent-title'>Añadir Punto de Interes a Evento</h2>
            <form className='addInterestPointToEvent-fields-container' onSubmit={handleSubmit}>
                <label className='addInterestPointToEvent-input-label'>
                    Descripcion:
                    <input className='addInterestPointToEvent-input'
                        type="text"
                        name="description"
                        value={interestPointData.description}
                        onChange={handleChange}
                        required
                    />
                </label>
                <br/>
                <label className='addInterestPointToEvent-input-label'>
                    Coordenada X:
                    <input className='addInterestPointToEvent-input'
                        type="number"
                        name="xCoordinate"
                        value={interestPointData.xCoordinate}
                        onChange={handleChange}
                        required
                    ></input>
                </label>
                <br/>
                <label className='addInterestPointToEvent-input-label'>
                    Coordenada Y:
                    <input className='addInterestPointToEvent-input'
                        type="number"
                        name="yCoordinate"
                        value={interestPointData.yCoordinate}
                        onChange={handleChange}
                        required
                    ></input>
                </label>
                <br/>
                <button button className='addInterestPointToEvent-submit' type="submit" disabled={loading}>
                    {loading ? 'Cargando...' : 'Añadir Punto de Interes a Evento'}
                </button>
            </form>

          </div>
        </div>
        <div className='interestPoint-list-container'>
            {responseMessage && (
                <p style={{color: responseMessage.includes('Error') ? 'red' : 'green'}}>
                    {responseMessage}
                </p>
            )}
            <InterestPointListFromEvent/>
        </div>
    </>
    );
}

const InterestPointListFromEvent = () => {
    const [interestPoints, setInterestPoints] = useState(null);
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();
    const {eventName} = useParams();
    const {eventDate} = useParams();

    useEffect(() => {

        FetchApi(
            `http://localhost:8080/stablishment/${stablishmentID}/event/${eventName}/${eventDate}/interestPoint/all`,
            'GET', undefined, token)

            .then((eventsJson) => {
                setInterestPoints(eventsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    });

    if (interestPoints === null) {
        return <p>Cargando datos...</p>;
    }

    const interestPointItems = interestPoints.map((interestPoint) =>
        <InterestPointItem key={interestPoint.interestPointID} interestPoint={interestPoint} />);

    return <ul className='interestPoint-lists'> {interestPointItems} </ul>;

}

export default AddInterestPointToEvent