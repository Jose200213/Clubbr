import React, {useEffect, useState} from "react";
import {CSSTransition} from "react-transition-group";

import {useAuth} from "../utils/TokenContext";
import FetchApi from "../utils/ApiUtils";
import {useParams} from "react-router-dom";

import "./LocalsComponents/AddWorker.css"

function WorkersItem({ worker }) {

    return (
        <li key={worker.id} className={`workers-item`}>
            <div className='workers-item-container'>
                <div className="workers-text">
                    <div className='workers-name'> {worker.userName} </div>
                    <div className='workers-desc'> {worker.userID} </div>
                    <div className='workers-desc'> {worker.eventID} </div>
                </div>
            </div>
        </li>
    );
}

function WorkersList() {
    const [workers, setWorkers] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();



    useEffect(() => {

        FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/worker/all`, 'GET', undefined, token)

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

    return <ul className='workers-lists'>{workersItems}</ul>;
}

export {WorkersItem, WorkersList}