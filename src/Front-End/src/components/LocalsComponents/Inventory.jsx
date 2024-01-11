import React, {useEffect, useState} from 'react';
import FetchApi from "../../utils/ApiUtils";
import {useAuth} from "../../utils/TokenContext";
import {useParams} from "react-router-dom";
import "./Inventory.css"

function InventoryList() {
    const [inventory, setInventory] = useState(null);
    const { getToken } = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();



    useEffect(() => {

        FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/item/all`, 'GET', undefined, token)

            .then((eventsJson) => {
                setInventory(eventsJson)
            })

            .catch((error) => {
                console.log(error);
            });

    });

    if (inventory === null) {
        return <p>Cargando datos...</p>;
    }

    const inventoryItems = inventory.map((item) =>
        <InventoryItem key={item.itemID} item={item} />);

    return <ul className='inventory-lists'> {inventoryItems} </ul>;
}

function InventoryItem({  item }) {

    return (
        <li key={item.interestPointID} className={`inventory-item`}>
            <div className='inventory-item-container'>
                <div className='inventory-text'>
                    <div className='inventory-name'> {item.itemName} </div>
                    <div className='inventory-desc'> {item.itemReference} </div>
                    <div className='inventory-quantity'> Cantidad disponible: {item.itemQuantity} </div>
                    <div className='inventory-stock'> En stock: {item.itemStock} </div>
                    <div className='inventory-price'> Precio: {item.itemPrice} </div>
                    <div className='inventory-distributor'> Distribuidor: {item.itemDistributor} </div>
                </div>
            </div>
        </li>
    );
}

const AddInventoryItem = () => {
    const {getToken} = useAuth();
    const token = getToken();
    const {stablishmentID} = useParams();

    const [loading, setLoading] = useState(false);
    const [responseMessage, setResponseMessage] = useState('');

    const [itemData, setItemData] = useState({
        stablishmentID: stablishmentID,
        itemReference: '',
        itemName: '',
        itemQuantity: 0,
        itemStock: 0,
        itemPrice: 0,
        itemDistributor: ''
    });

    const handleChange = (e) => {
        const {name, value} = e.target;
        setItemData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            console.log(itemData)
            const response = await FetchApi(`http://localhost:8080/stablishment/${stablishmentID}/item/add`,
                'POST', itemData, token)
            setResponseMessage(response);

        } catch (error) {
            console.error('Error al crear el item:', error);

            setResponseMessage(`${error.name} - ${error.message}`);
            console.log(responseMessage)

        } finally {
            setLoading(false);
        }
    };

    return (
        <>
        <div className='inventory-container'>
            <div className="inventory-shape">
                <h2 className='inventory-title'>Añadir item al inventario</h2>
                <form className='inventory-fields-container' onSubmit={handleSubmit}>
                    <label className='inventory-input-label'>
                        Referencia:
                        <input className='inventory-input'
                            type="text"
                            name="itemReference"
                            value={itemData.itemReference}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='inventory-input-label'>
                        Nombre:
                        <input className='inventory-input'
                            type="text"
                            name="itemName"
                            value={itemData.itemName}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='inventory-input-label'>
                        Cantidad:
                        <input className='inventory-input'
                            type="number"
                            name="itemQuantity"
                            value={itemData.itemQuantity}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='inventory-input-label'>
                        Stock:
                        <input className='inventory-input'
                            type="number"
                            name="itemStock"
                            value={itemData.itemStock}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='inventory-input-label'>
                        Precio:
                        <input className='inventory-input'
                            type="number"
                            name="itemPrice"
                            value={itemData.itemPrice}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <label className='inventory-input-label'>
                        Distribuidor:
                        <input className='inventory-input'
                            type="text"
                            name="itemDistributor"
                            value={itemData.itemDistributor}
                            onChange={handleChange}
                            required
                        />
                    </label>
                    
                    <button className='inventory-submit' type="submit" disabled={loading}>
                        {loading ? 'Cargando...' : 'Añadir item'}
                    </button>
                </form>
                
                
                

            </div>
        </div>

        <div className='inventory-list-container'>
            {responseMessage && ( <p style={{color: responseMessage.includes('Error') ? 'red' : 'green'}}> {responseMessage} </p> )}
            <InventoryList/>
        </div>
        
        </>
    );
}

export default AddInventoryItem