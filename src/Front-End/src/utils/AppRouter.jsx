import React from 'react';
import { Route, Routes, Navigate } from 'react-router-dom';

import Login from "../components/Login";
import Locals from "../components/Locals";
import {EventsList} from "../components/Events";
import Tickets from "../components/Tickets";
import CreateEvent from "../components/LocalsComponents/CreateEvent";
import AddWorker from "../components/LocalsComponents/AddWorker";
import AddInterestPoint from "../components/LocalsComponents/InterestPoint";
import AddInventoryItem from "../components/LocalsComponents/Inventory";
import AddWorkerToEvent from "../components/EventsComponents/AddWorkerToEvent";
import AddInterestPointToEvent from "../components/EventsComponents/AddInterestPointToEvent";
import AddTicket from "../components/EventsComponents/AddTicket";

const AppRouter = () => {
    return (

        <Routes>

            <Route path="/"  element={<Navigate to="/login" replace />}
            />
            <Route path="/login" element={<Login />} />

            <Route path="/locals" element={<Locals />} />
            <Route path="/events" element={<EventsList />} />
            <Route path="/tickets" element={<Tickets />} />

            <Route path="/:stablishmentID/createEvent" element={<CreateEvent />} />
            <Route path="/:stablishmentID/addWorker" element={<AddWorker />} />
            <Route path="/:stablishmentID/addInterestPoint" element={<AddInterestPoint />} />
            <Route path="/:stablishmentID/inventory" element={<AddInventoryItem />} />
            <Route path="/:stablishmentID/:eventName/:eventDate/addWorker" element={<AddWorkerToEvent />} />
            <Route path="/:stablishmentID/:eventName/:eventDate/addInterestPoint" element={<AddInterestPointToEvent />} />
            <Route path="/:stablishmentID/:eventName/:eventDate/addTicket" element={<AddTicket />} />




        </Routes>

    );
};

export default AppRouter;
