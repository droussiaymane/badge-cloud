import { isUndefined } from 'lodash';
import axios from '../utils/axios';
import parse from '../utils/parse';

class competencyApi {
    async create(values, tenantId) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies`, values, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        createdId: 1,
                    },
                })
                .then((response) => {
                    response = parse(response);
                    console.log('response', response);
                    if (!isUndefined(response?.data)) {
                        const res = response?.data;
                        if (res?.code == 0) {
                            reject(new Error(res.message));
                        }
                        else
                            resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }

    async edit(values, tenantId) {
        console.log('Values', values);
        return new Promise((resolve, reject) => {
            axios
                .post(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies`, values, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                    },
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response?.data)) {
                        const res = response?.data
                        if (res?.code == 0) {
                            reject(new Error(res.message));
                        }
                        else
                            resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }

    async getAll(page, limit) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies?page=${page}&limit=${limit}`, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                    },
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        const res = response?.data?.data;
                        resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }

    async getById(id) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies/${id}`, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                    },
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response?.data)) {
                        const res = response?.data?.data;
                        resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }

    async deleteById(id) {
        return new Promise((resolve, reject) => {
            axios
                .delete(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies/${id}`, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                    },
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response?.data)) {
                        const res = response?.data?.data;
                        resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }

    async getAssignRecords() {
        return new Promise((resolve, reject) => {
            axios
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies/assign`, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                    },
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        const res = response?.data?.data;
                        resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }

    async searchByName(search) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/competencies/searchByCompetencyName?competencyName=${search}`, {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                    },
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        const res = response?.data?.data;
                        resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log(error, 'error');
                    reject(new Error('Internal Server Error'));
                });
        });
    }
}

export const CompetencyApi = new competencyApi();
