import React, {useEffect, useState} from 'react';
import { CSSTransition } from 'react-transition-group';
import { If, Then, ElseIf, Else } from 'react-if-elseif-else-render';

import './App.css';

import {AuthProvider} from "./utils/TokenContext"
import Login from "./components/Login/Login";

import {PageNav, NavDisplay, NavFixed} from "./components/Navs/Navs";

import LocalsList from "./components/Locals/Locals"
import EventsList from "./components/Events/Events";
import TicketsList from "./components/Tickets/Tickets";

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
    const Options = ['Locals', 'Events', 'Tickets']
    const Menus = ['Login', 'User']
    const [active, setActive] = useState(Options[2])
    const [menu, setMenu] = useState(Menus[1])

    const HandleLogin = () => {
        // Esta función se pasa como prop al LoginForm y se llama cuando se realiza el inicio de sesión
        setMenu(Menus[1])
    };




    return (
    <AuthProvider>
    
            <Background>

                <If condition={menu === 'Login'}>

                    <Then>
                        <Login onLogin={HandleLogin}/>
                    </Then>

                    <Else>

                        <PageNav>

                            <If condition={active === 'Locals'}>
                                <Then>
                                    <NavFixed icon={localsicon} text="LOCALES"/>
                                    <NavDisplay icon={eventsicon} value={Options[1]} stateFunc={setActive}/>
                                    <NavDisplay icon={ticketicon} value={Options[2]} stateFunc={setActive}/>
                                </Then>

                                <ElseIf condition={active === 'Events'}>
                                    <NavDisplay icon={localsicon} value={Options[0]} stateFunc={setActive}/>
                                    <NavFixed icon={eventsicon} text="EVENTOS"/>
                                    <NavDisplay icon={ticketicon} value={Options[2]} stateFunc={setActive}/>
                                </ElseIf>

                                <Else>
                                    <NavDisplay icon={localsicon} value={Options[0]} stateFunc={setActive}/>
                                    <NavDisplay icon={eventsicon} value={Options[1]} stateFunc={setActive}/>
                                    <NavFixed icon={ticketicon} text="TICKETS"/>
                                </Else>
                            </If>

                        </PageNav>


                        <If condition={menu === 'User'}>
                            <Then>
                                <CSSTransition in={active === 'Locals'} appear={true} unmountOnExit timeout={1000} classNames={'list-animation'}>
                                    <div className='list-animation-container'>
                                        <LocalsList locals/>
                                    </div>
                                </CSSTransition>
              
                                <CSSTransition in={active === 'Events'} appear={true} unmountOnExit timeout={1000} classNames={'list-animation'}>
                                    <div className='list-animation-container'>
                                        <EventsList events/>
                                    </div>
                                </CSSTransition>

                                {/*<CSSTransition in={active === 'Tickets'} appear={true} unmountOnExit timeout={1000} classNames={'list-animation'}>
                                    <div className='list-animation-container'>*/}
                                        <TicketsList tickets/>
                                    {/*</div>
                                </CSSTransition>*/}
                            </Then>
                        </If>

                    </Else>
                </If>
            </Background>

    </AuthProvider>
  );
}

export default App;
