
const FetchApi = async (url, mode, body, token) => {
    try {
        const headers = {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        };

        const opciones = {
            method: mode,
            headers: headers,
            body: (mode === 'POST' || mode === 'PUT') ? JSON.stringify(body) : undefined
        }

        // Hacer la solicitud
        const response = await fetch(url, opciones);

        // Verificar si la solicitud fue exitosa (código de estado 200)
        if (response.ok) {

            const contentType = response.headers.get('Content-Type');

            if (contentType && contentType.includes('text/plain')) {
                // Si es texto, devolver directamente el texto
                return response.text();
            } else {
                // Si no es texto, convertir la respuesta a formato JSON
                return response.json();
            }
        }
        else {
            const errorMessage =  await response.text()
            throw new Error(`${response.status} - ${errorMessage}`);
        }
    } catch (error) {
        throw error;
    }
}

export default FetchApi