import React from "react";
import { Link } from 'react-router-dom';
import {ElseIf, If, Then} from "react-if-elseif-else-render";
import localsicon from "../svg/localsicon.svg";
import eventsicon from "../svg/eventicon.svg";
import ticketicon from "../svg/ticketicon.svg";
import {Else} from "react-if-elseif-else-render/src";

import "./Navs/Navs.css"

function PageNav({currentPath}){

    if (currentPath === '/login' ){
        return null
    }
    return (
        <>
            <div className="general-nav">
                <If condition={currentPath === '/locals'}>
                    <Then>
                        <NavFixed icon={localsicon} text="LOCALES"/>
                        <NavDisplay icon={eventsicon} to = '/events'/>
                        <NavDisplay icon={ticketicon} to = '/tickets'/>
                    </Then>

                    <ElseIf condition={currentPath === '/events'}>
                        <NavDisplay icon={localsicon} to = '/locals'/>
                        <NavFixed icon={eventsicon} text="EVENTOS"/>
                        <NavDisplay icon={ticketicon} to = '/tickets'/>
                    </ElseIf>

                    <ElseIf condition={currentPath === '/tickets'}>
                        <NavDisplay icon={localsicon} to = '/locals'/>
                        <NavDisplay icon={eventsicon} to = '/events'/>
                        <NavFixed icon={ticketicon} text="TICKETS"/>
                    </ElseIf>
                    <Else>
                        <NavDisplay icon={localsicon} to = '/locals'/>
                        <NavDisplay icon={eventsicon} to = '/events'/>
                        <NavDisplay icon={ticketicon} to = '/tickets'/>
                    </Else>
                </If>
            </div>
        </>
    );
}

function NavFixed({icon, text}) {
    return (
        <>
            <div className="fixed-container">
                <div className="fixed-shape">
                    <img className="fixed-icon" alt="Nav icon" src={icon}/>
                    <div className="fixed-text">
                        <div className="button-text">{text}</div>
                    </div>
                </div>
            </div>
        </>
    )

}

function NavDisplay({icon, to}) {
    return (
        <div className="display-container">
            <div className="display-button">
                <Link to={to}>
                    <img className="display-icon" alt="Display icon" src={icon}/>
                </Link>
            </div>
        </div>
    );
}

export  {PageNav, NavFixed, NavDisplay}