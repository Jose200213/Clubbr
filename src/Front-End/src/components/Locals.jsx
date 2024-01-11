import React, {useEffect, useState} from "react";
import eventsicon from "../svg/eventicon.svg";
import {CSSTransition} from "react-transition-group";

import {useAuth} from "../utils/TokenContext";
import FetchApi from "../utils/ApiUtils";
import {Link} from "react-router-dom";

import "./Locals/Locals.css"

function LocalsItem({local}) {
    const [dropdown, setDropdown] = useState(false);

    return (
        <li key={local.stablishmentID} className={`locals-item ${dropdown ? 'push-down' : ''}`}>
            <div className='locals-item-container'>
                <div className='locals-logo'>
                    <img className='locals-image' src={'https://placehold.co/125'} alt='logo'/>
                </div>
                
                <div className="locals-text">
                    <div className='locals-name'> {local.stabName} </div>
                    <div className='locals-desc'> {local.stabName} </div>
                </div>

                <img className="locals-goTo" alt="Event icon" src={eventsicon}
                     onClick={() => setDropdown(!dropdown)}
                />
            </div>

            <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
                <div className='locals-item-dropdown'>
                    <div className='locals-dropdown-container'>
                        <ul className='locals-dropdown-list'>
                            <li className='locals-dropdown-element'>
                                <Link to={`/${local.stablishmentID}/createEvent`}> Eventos </Link>
                            </li>
                            <li className='locals-dropdown-element'>
                                <Link to={`/${local.stablishmentID}/addWorker`}> Trabajadores </Link>
                            </li>
                            <li className='locals-dropdown-element'>
                                <Link to={`/${local.stablishmentID}/inventory`}> Inventario </Link>

                            </li>
                            <li className='locals-dropdown-element'>
                                <Link to={`/${local.stablishmentID}/addInterestPoint`}> Puntos de Interes </Link>
                            </li>

                        </ul>
                    </div>
                </div>
            </CSSTransition>
        </li>
    );
}

function LocalsList() {
    const [locals, setLocals] = useState(null);
    const {getToken} = useAuth();
    const token = getToken();


    useEffect(() => {

        FetchApi('http://localhost:8080/stablishment/all', 'GET', undefined, token)

            .then((localsJson) => {
                setLocals(localsJson);
            })

            .catch((error) => {
                console.log(error);
            });

    }, []);

    if (locals === null) {
        return <p>Cargando datos...</p>;
    }

    const eventsItems = locals.map((local) =>
        <LocalsItem key={local.stablishmentID} local={local}/>);

    return <ul className={'locals-lists'}> {eventsItems} </ul>

}

export default LocalsList;