import React, { useState } from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import {InterestPointList} from "./InterestPointList";
import "./InterestPoint.css"


const AddInterestPoint = () => {
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();

    const [loading, setLoading] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');

    const [interestPointData, setInterestPointData] = useState({
        stablishmentID: stablishmentID,
        description: '',
        xCoordinate: 0,
        yCoordinate: 0,
        eventName: 'NULL',
        eventDate: 'NULL',

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
            const response = await FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/interestPoint/add`,
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
        <div className="interestPoint-container">
            <div className = "interestPoint-shape">
                <h2 className='interestPoint-title'>Crear Punto de Interes</h2>
                <form className='interestPoint-fields-container' onSubmit={handleSubmit}>
                    <label className='interestPoint-input-label'>
                        Descripcion:
                        <input className='interestPoint-input'
                            type="text"
                            name="description"
                            value={interestPointData.description}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='interestPoint-input-label'>
                        Coordenada X:
                        <input className='interestPoint-input'
                            type="number"
                            name="xCoordinate"
                            value={interestPointData.xCoordinate}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='interestPoint-input-label'>
                        Coordenada Y:
                        <input className='interestPoint-input'
                            type="number"
                            name="yCoordinate"
                            value={interestPointData.yCoordinate}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <button className='interestPoint-submit' type="submit" disabled={loading}>
                        {loading ? 'Cargando...' : 'Añadir Punto de Interes'}
                    </button>
                </form>
            </div>
        </div>

        <div className='interestPoint-list-container'>
            {responseMessage && (
                <p style={{color: responseMessage.includes('Error') ? 'red' : 'green'}}> {responseMessage} </p>
            )}
            <InterestPointList/>
        </div>
        </>
    );
}


export default AddInterestPoint