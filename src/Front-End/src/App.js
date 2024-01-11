import './App.css';

import {AuthProvider} from "./utils/TokenContext"

import {PageNav, NavDisplay, NavFixed} from "./components/Navs"

import { If, Then, ElseIf, Else } from 'react-if-elseif-else-render';
import {useLocation, Navigate, useNavigate} from "react-router-dom";
import AppRouter from "./utils/AppRouter";
import {useEffect, useState} from "react";

import eventsicon from './svg/eventicon.svg'
import localsicon from './svg/localsicon.svg'
import ticketicon from './svg/ticketicon.svg'


/**************************************************************************************/
/********************************** GENERALES *****************************************/
/**************************************************************************************/

function Background({children}) {
  return ( 
    <div className='background-colorizer'>
      <div className='background-wrapper'>
          <div className={'background-image'}>
          {children}
          </div>
      </div>
    </div>
  )
}


/********************************************************************************/
/********************************** APP *****************************************/
/********************************************************************************/

const App = () => {
    const Options = ['locals', 'events', 'tickets']
    const Titles = ['LOCALES', 'EVENTOS', 'TICKETS']
    const Icons = [localsicon, eventsicon, ticketicon]
    const [active, setActive] = useState(Options[0])

    const location = useLocation();
    const currentPath = location.pathname;
    console.log(location)



    return (
      <AuthProvider>
        <Background>
          <AppRouter />
          <PageNav currentPath={currentPath} />
        </Background>
      </AuthProvider>
    );
}

export default App