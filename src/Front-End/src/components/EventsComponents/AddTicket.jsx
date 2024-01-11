import React, {useEffect, useState} from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useHistory, useParams} from "react-router-dom";

function AddTicket(){
    const [events, setEvents] = useState([]);
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID, eventName, eventDate} = useParams();


    useEffect(() => {

        FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/event/${eventName}/${eventDate}/ticket/add`,
            'POST', undefined, token)

            .then((eventsJson) => {
                setEvents(eventsJson)
            })

            .catch((error) => {
                console.log(error);
                setEvents(error.message)
            });

    }, []);

    if (events === null) {
        return <p>Cargando datos...</p>;
    }

       return (
           <div>
               ${events}
           </div>
       )


}
export default AddTicket;