import axios from 'axios';
import { isUndefined } from 'lodash';
// @ts-ignore  
import jwtDecode from 'jwt-decode';
import parse from '../utils/parse';
import { studentApi } from './userApi';

class AuthApi {
    async login(username, password) {
        const body = {
            username,
            password
        }
        const url = `${process.env.REACT_APP_KEYCLOAK_URL}/user/login`
        return new Promise(async (resolve, reject) => {
            axios
                .post(url, body)
                .then(async (response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        if (response?.data?.code == 0) {
                            reject(response?.data?.message);
                        }
                        else {
                            let res = response?.data?.data;
                            const decoded = jwtDecode(res?.access_token);
                            if (decoded?.realm_access?.roles?.includes("Administrator")) {
                                reject('Unauthorized user access');
                            }
                            const getKUser = await this.getUserByKid(decoded?.sub, res.access_token);
                            if (getKUser?.code) {
                                res['userId'] = getKUser?.data
                                resolve(res);
                            }
                            else {
                                reject("Error Occurred please contact your Administrator")
                            }
                        }
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log("error", error)
                    if (error?.response?.status == 401 || error?.response?.status == 403 || error?.response?.status) {
                        reject(error.response.statusText);
                    }

                    reject('Internal Server Error');
                });
        });
    }

    async getUserByKid(id, token) {
        const url = `${process.env.REACT_APP_USERS_API_URL}/users/getKUser/${id}`;
        console.log("url", url);
        return new Promise((resolve, reject) => {
            axios
                .get(url, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                })
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        const res = response?.data;
                        resolve(res);
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    if (error?.response?.status == 401 || error?.response?.status == 403 || error?.response?.status) {
                        reject(error.response.statusText);
                    }
                    reject('Internal Server Error');
                });
        });
    }

    async RefreshTokenApi(refreshToken) {
        const body = {
            refresh_token: refreshToken
        }
        const url = `${process.env.REACT_APP_KEYCLOAK_URL}/user/refreshToken`
        console.log("called", url)
        return new Promise((resolve, reject) => {
            axios
                .post(url, body)
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        if (response?.data?.code == 0) {
                            reject(response?.data?.message);
                        }
                        else {
                            const res = response?.data?.data;
                            resolve(res);
                        }
                    } else {
                        reject(new Error('Invalid Server Response'));
                    }
                })
                .catch((error) => {
                    console.log("error", error)
                    if (error?.response?.status == 401 || error?.response?.status == 403 || error?.response?.status) {
                        reject(error.response.statusText);
                    }

                    reject('Internal Server Error');
                });
        });
    }
    async getPublicKey() {
        const url = `http://localhost:8080/auth/realms/demo-realm/protocol/openid-connect/certs`
        return new Promise((resolve, reject) => {
            axios
                .get(url)
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        console.log("login", response)
                        const res = response?.data;
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

export const authApi = new AuthApi();
