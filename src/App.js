import React, { useState } from 'react';
import { CSSTransition } from 'react-transition-group';
import { If, Then, ElseIf, Else } from 'react-if-elseif-else-render';

import './App.css';
import { locals } from './locals';
import { events } from './events';
import { tickets } from './tickets';

import eventsicon from './svg/eventicon.svg'
import localsicon from './svg/localsicon.svg'
import ticketicon from './svg/ticketicon.svg'
import moon from './svg/moonlogo.svg'
import passw from './svg/solar-password-bold.svg'
import user from './svg/mdi-user.svg'

/****** TO DO'S ******/

/* SCREEN RESIZE */


/**************************************************************************************/
/********************************** GENERALES *****************************************/
/**************************************************************************************/

function Background({children}) {
  return ( 
    <div className='background-colorizer'>
      <div className='background-wrapper'>
        <div className='background-image'>
          {children}
        </div>
      </div>
    </div>
  )
}

function Login({value, stateFunc}) {
  return (
    <>
      <div className="login-container">
        <div className='login-logo-container'>
          <img className="login-logo-image" alt="Vector" src={moon}/>
          <div className="login-logo-text">CLUBB’r</div>
        </div>
        <div className="login-user-input-container">
          <input className="username" placeholder='username'/>
          <img className="login-user-icon" alt="Mdi user" src={user} />
        </div>
        <div className="login-passw-input-container">
          <input className="password" placeholder='password'/>
          <img className="login-passw-icon" alt="Solar password bold" src={passw} />
        </div>
        <div className="login-submit" onClick={() => stateFunc(value)}>
          <h2>Send</h2>
        </div>
      </div>
    </>
  )
}

/**************************************************************************************/
/*********************************** NAVEGADOR ****************************************/
/**************************************************************************************/

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

/***********************************************************************************/
/*********************************** LISTAS ****************************************/
/***********************************************************************************/

/****************** LOCALES ******************/

function LocalsItem({ local }) {
  const [dropdown, setDropdown] = useState(false);

  return (
    <li key={local.id} className={`page-lists-item ${dropdown ? 'push-down' : ''}`}>
      <div className='page-lists-item-container'>
        <div className='page-lists-item-container-ellipse'>
          <img className='page-lists-item-container-logo' src={local.logoUrl} alt='logo' />
        </div>
        <img className="page-lists-item-container-event" alt="Event icon" src={eventsicon} onClick={() => setDropdown(!dropdown)}
        />
        <div className='page-lists-item-container-name'> {local.name} </div>
        <div className='page-lists-item-container-desc'> {local.schedule} </div>
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
  const localsItems = locals.map((local) => <LocalsItem key={local.id} local={local} />);

  return <ul className='page-lists'>{localsItems}</ul>;
}

/****************** EVENTOS ******************/

function EventsItem({ evento }) {
  const [dropdown, setDropdown] = useState(false);

  return (
    <li key={evento.id} className={`page-lists-item ${dropdown ? 'push-down' : ''}`}>
      <div className='page-lists-item-container'>
        <div className='page-lists-item-container-ellipse'>
          <img className='page-lists-item-container-logo' src={evento.logoUrl} alt='logo' />
        </div>
        <img className="page-lists-item-container-event" alt="Event icon" src={ticketicon} onClick={() => setDropdown(!dropdown)}
        />
        <div className='page-lists-item-container-name'> {evento.name} </div>
        <div className='page-lists-item-container-desc'> {evento.schedule} </div>
      </div>
      <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
        <div className='page-lists-item-dropdown'>
          <div className='page-lists-item-dropdown-events'>
            <ul className='dropdown-events-list'>
              <li className='dropdown-events-list-element'>item1</li>
              <li className='dropdown-events-list-element'>item2</li>
              <li className='dropdown-events-list-element'>item3</li>
            </ul>
          </div>
        </div>
      </CSSTransition>
    </li>
  );
}

function EventsList() {
  const eventsItems = events.map((evento) => <EventsItem key={evento.id} evento={evento} />);

  return <ul className='page-lists'>{eventsItems}</ul>;
}

/****************** TICKETS ******************/

function TicketsItem({ ticket }) {
  const [dropdown, setDropdown] = useState(false);

  return (
    <li key={ticket.id} className={`page-lists-item ${dropdown ? 'push-down' : ''}`}>
      <div className='page-lists-item-container'>
        <img className="page-lists-item-container-event" alt="Event icon" src={eventsicon} onClick={() => setDropdown(!dropdown)}/>
        <div className='page-lists-item-container-name'> {ticket.name} </div>
        <div className='page-lists-item-container-desc'> {ticket.schedule} </div>
      </div>
      <CSSTransition in={dropdown} unmountOnExit timeout={500} classNames={'dropdown'}>
        <div className='page-lists-item-dropdown'>
          <div className='page-lists-item-dropdown-tickets'>
            <ul className='dropdown-tickets-list'>
              <li className='dropdown-tickets-list-element'>item1</li>
              <li className='dropdown-tickets-list-element'>item2</li>
              <li className='dropdown-tickets-list-element'>item3</li>
            </ul>
          </div>
          
        </div>
      </CSSTransition>
    </li>
  );
}

function TicketsList() {
  const ticketsItems = tickets.map((ticket) => <TicketsItem key={ticket.id} ticket={ticket} />);

  return <ul className='page-lists'>{ticketsItems}</ul>;
}

/********************************************************************************/
/********************************** APP *****************************************/
/********************************************************************************/

const App = () => {
  const Options = ['Locals', 'Events', 'Tickets']
  const Menus = ['Login', 'User']
  const [active, setActive] = useState(Options[0])
  const [menu, setMenu] = useState(Menus[0])

  return (
    <>
    <div style={{height: '100vh', width: '100vw'}}>
      <Background>

        <If condition={menu === 'Login'}>
          <Then>

            <Login value={Menus[1]} stateFunc={setMenu}/>

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
                <CSSTransition in={active === 'Locals'} appear={true} unmountOnExit timeout={1000} classNames={'locals-list-animation'}>
                  <div className='list-animation-container'>
                    <LocalsList locals/>
                  </div>
                </CSSTransition>
              
                <CSSTransition in={active === 'Events'} appear={true} unmountOnExit timeout={1000} classNames={'events-list-animation'}>
                  <div className='list-animation-container'>
                    <EventsList events/>
                  </div>
                </CSSTransition>

                <CSSTransition in={active === 'Tickets'} appear={true} unmountOnExit timeout={1000} classNames={'tickets-list-animation'}>
                  <div className='list-animation-container'>
                    <TicketsList tickets/>
                  </div>
                </CSSTransition>
              </Then>
            </If>

          </Else>
        </If>

        
        

      </Background>
      </div>
    </>
  );
}

export default App;
