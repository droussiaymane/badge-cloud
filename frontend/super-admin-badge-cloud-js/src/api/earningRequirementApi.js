import { isUndefined } from 'lodash';
import axios from '../utils/axios';
import parse from '../utils/parse';

class earningRequirementApi {
    async create(values, tenantId) {
        return new Promise((resolve, reject) => {
            axios
                .post(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements`, values, {
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
                .post(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements`, values, {
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

    async getAll(tenantId, page, limit) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements?page=${page}&limit=${limit}`, {
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
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements/${id}`, {
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
                .delete(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements/${id}`, {
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

    async getAssignRecords(tenantId) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements/assign`, {
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
            .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/earningRequirements/searchByName?name=${search}`, {
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

export const EarningRequirementsApi = new earningRequirementApi();
