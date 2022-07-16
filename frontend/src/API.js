import * as config from './config';

export class API {
    get(path, queryParams) {
        const url = 'http://localhost:8080/' + path + '?' + this.queryString(queryParams);
        return fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.json().then((json) => {
                        throw json.error;
                    });
                }
            })
            .catch((error) => {
                console.error(error);
                throw new Error('No se pudo comunicar con el servidor correctamente');
            });
    }

    post(path, body) {
        const url = 'http://localhost:8080/' + path;
        return fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json'
            },
            body: JSON.stringify(body)
        })
            .then((response) => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.json().then((json) => {
                        throw json.error;
                    });
                }
            })
            .catch((error) => {
                console.error(error);
                throw new Error(`${error}`);
            });
    }

    queryString(queryParams) {
        if (!queryParams) {
            return '';
        }

        return Object.keys(queryParams)
            .map((key) => key + '=' + queryParams[key])
            .join('&');
    }
}
