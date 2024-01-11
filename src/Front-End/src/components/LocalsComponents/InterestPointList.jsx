import React, {useEffect, useState} from "react";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import FetchApi from "../../utils/ApiUtils";

function InterestPointList() {
    const [interestPoints, setInterestPoints] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();



    useEffect(() => {

        FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/interestPoint/all`, 'GET', undefined, token)

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

function InterestPointItem({ interestPoint }) {

    return (
        <li key={interestPoint.interestPointID} className={`interestPoint-item`}>
            <div className='interestPoint-item-container'>
                <div className="interestPoint-text">
                    <div className='interestPoint-name'> {interestPoint.interestPointID} </div>
                    <div className='interestPoint-desc'> {interestPoint.description} </div>
                </div>
            </div>
        </li>
    );
}

export  {InterestPointList, InterestPointItem};