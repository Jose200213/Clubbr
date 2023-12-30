import React, {useEffect, useState} from "react";
import eventsicon from "../../svg/eventicon.svg";
import {CSSTransition} from "react-transition-group";

import {useAuth} from "../../utils/TokenContext";
import FetchApi from "../../utils/ApiUtils";

function LocalsItem({ local }) {
    const [dropdown, setDropdown] = useState(false);

    return (
        <li key={local.stablishmentID} className={`page-lists-item ${dropdown ? 'push-down' : ''}`}>
            <div className='page-lists-item-container'>
                <div className='page-lists-item-container-ellipse'>
                    <img className='page-lists-item-container-logo' src={'https://placehold.co/125'} alt='logo' />
                </div>
                <img className="page-lists-item-container-event" alt="Event icon" src={eventsicon} onClick={() => setDropdown(!dropdown)}
                />
                <div className='page-lists-item-container-name'> {local.stabName} </div>
                <div className='page-lists-item-container-desc'> {local.stabName} </div>
            </div>

            <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
                <div className='page-lists-item-dropdown'>
                    <div className='page-lists-item-dropdown-locals'>
                        <ul className='dropdown-locals-list'>
                            <li className='dropdown-locals-list-element'>item1</li>
                            <li className='dropdown-locals-list-element'>item2</li>
                            <li className='dropdown-locals-list-element'>item3</li>
                        </ul>
                    </div>
                </div>
            </CSSTransition>
        </li>
    );
}

function LocalsList() {
    const [locals, setLocals] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();


    useEffect(() => {

        FetchApi('http://localhost:8080/stablishment/all', 'GET', undefined, token)

            .then((localsJson) => {
                setLocals(localsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    }, []);

    if (locals === null) {
        return <p>Cargando datos...</p>;
    }

    const eventsItems = locals.map((local) =>
        <LocalsItem key={local.stablishmentID} local={local} />);

    return <ul className={'page-lists'}> {eventsItems} </ul>;


}

export default LocalsList;