import React, {useEffect, useState} from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import {WorkersItem} from "../Workers";
import "./AddWorkerToEvent.css"


const AddWorkerToEvent = () => {
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();
    const {eventName} = useParams();
    const {eventDate} = useParams();

    const [loading, setLoading] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');

    const [workerData, setWorkerData] = useState({
        userID: {userID: ''},
        stablishmentID: stablishmentID,
        attendance: 0,
        salary: 0,
        workingHours: 0,
        eventID: {eventName: `${eventName}`, eventDate: `${eventDate}`},


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
        <div className = "addWorkerToEvent-container">
          <div className = "addWorkerToEvent-shape">
             <h2 className='addWorkerToEvent-title'>Crear Trabajador</h2>
             <form className='addWorkerToEvent-fields-container' onSubmit={handleSubmit}>
                <label  className='addWorkerToEvent-input-label'>
                    ID de Usuario:
                    <input className='addWorkerToEvent-input'
                        type="text"
                        name="userID"
                        value={workerData.userID.userID}
                        onChange={handleChange}
                        required
                    />
                </label>
                
                <label className='addWorkerToEvent-input-label'>
                    Salario:
                    <input className='addWorkerToEvent-input'
                        type="number"
                        name="salary"
                        value={workerData.salary}
                        onChange={handleChange}
                        required
                    ></input>
                </label>
                
                <label className='addWorkerToEvent-input-label'>
                    Horas de Trabajo:
                    <input className='addWorkerToEvent-input'
                        type="number"
                        name="workingHours"
                        value={workerData.workingHours}
                        onChange={handleChange}
                        required
                    ></input>
                </label>
                
                <button className='addWorkerToEvent-submit' type="submit" disabled={loading}>
                    {loading ? 'Cargando...' : 'Añadir Trabajador Eventual'}
                </button>
             </form>

            </div>
        </div>
        <div className='WorkersList-container'>
            {responseMessage && (
                <p style={{color: responseMessage.includes('Error') ? 'red' : 'green'}}> {responseMessage} </p>
            )}
            <WorkersList/>
        </div>
    </>
    );
}


function WorkersList() {
    const [workers, setWorkers] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();
    const {eventName} = useParams();
    const {eventDate} = useParams();




    useEffect(() => {

        FetchApi(
            `http://localhost:8080/stablishment/${stablishmentID}/event/${eventName}/${eventDate}/worker/all`,
            'GET', undefined, token)

            .then((eventsJson) => {
                setWorkers(eventsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    });

    if (workers === null) {
        return <p>Cargando datos...</p>;
    }

    const workersItems = workers.map((worker) =>
        <WorkersItem key={worker.id} worker={worker} />);

    return <ul className='workers-lists'> {workersItems} </ul>
}

export default AddWorkerToEvent