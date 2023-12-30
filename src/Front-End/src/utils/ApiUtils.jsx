import { useAuth } from "./TokenContext"


const FetchApi = async (url, mode, body, token) => {
    try {
        const headers = {
            'Authorization': `Bearer ${token}`,
        };

        const opciones = {
            method: mode,
            headers: headers,
            body: (mode === 'POST' || mode === 'PUT') ? JSON.stringify(body) : undefined
        }

        // Hacer la solicitud
        const respuesta = await fetch(url, opciones);

        // Verificar si la solicitud fue exitosa (código de estado 200)
        if (respuesta.ok) {
            // Convertir la respuesta a formato JSON
            return respuesta.json();


        } else {
            console.error('Error al obtener los datos:', respuesta.status);
            throw new Error(`Error en la solicitud a ${url}`);
        }
    } catch (error) {
        console.error('Error de red:', error);
        throw error;
    }
}

export default FetchApi