import React, { useState } from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import {WorkersList} from "../Workers";
import "./AddWorker.css"


const AddWorker = () => {
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();

    const [loading, setLoading] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');

    const [workerData, setWorkerData] = useState({
        userID: {userID: ''},
        stablishmentID: stablishmentID,
        attendance: 1,
        salary: 0,
        workingHours: 160,
        eventName: 'NULL',
        eventDate: 'NULL',

    });

    const handleChange = (e) => {
        const {name, value} = e.target;
        setWorkerData((prevData) => ({
            ...prevData,
            [name]: name === 'userID' ? { userID: value } : value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            console.log(workerData)
            const response = await FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/worker/add`,
                'POST', workerData, token)
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
        <div className='addWorker-container'>
            <div className="addWorker-shape">
                <h2 className="addWorker-title">Crear Trabajador</h2>
                <form className='addWorker-fields-container' onSubmit={handleSubmit}>
                    <label className='addWorker-input-label'>
                        ID de Usuario:
                        <input className='addWorker-input'
                            type="text"
                            name="userID"
                            value={workerData.userID.userID}
                            onChange={handleChange}
                            required
                        />
                    </label>

                    <label className='addWorker-input-label'>
                        Salario:
                        <textarea className='addWorker-salary-input'
                            type="number"
                            name="salary"
                            value={workerData.salary}
                            onChange={handleChange}
                            required
                        ></textarea>
                    </label>

                    <button className='addWorker-submit' type="submit" disabled={loading}>
                        {loading ? 'Cargando...' : 'Añadir Trabajador Fijo'}
                    </button>
                </form>
                    
            </div>
        </div>
        
        <div className='workers-list-container'>
            {responseMessage && (
                <p style={{color: responseMessage.includes('Error') ? 'red' : 'green'}}>
                    {responseMessage}
                </p>
            )}
            <WorkersList/>
        </div>
    </>
)
    ;
}

export default AddWorker