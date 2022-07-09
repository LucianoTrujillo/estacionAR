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
        }).then((response) => response.json());
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