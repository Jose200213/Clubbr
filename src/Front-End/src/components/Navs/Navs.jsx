import React from "react";

import "./Navs.css"

function PageNav({children}){
    return (
        <>
            <div className="general-nav">
                {children}
            </div>
        </>
    );
}

function NavFixed({icon, text}) {
    return (
        <>
            <div className="fixed-button">
                <div className="fixed-button-container">
                    <div className="fixed-button-container-shape" />
                    <img className="fixed-button-container-icon" alt="Nav icon" src={icon}/>
                    <div className="fixed-button-container-text">{text}</div>
                </div>
            </div>
        </>
    )

}

function NavDisplay({icon, value, stateFunc}) {
    return (
        <>
            <div className="display-button">
                <div className='display-button-container'>
                    <img className='display-button-container-icon' alt="Display icon" src ={icon} onClick={() => stateFunc(value)}/>
                </div>
            </div>
        </>
    )
}

export  {PageNav, NavFixed, NavDisplay}