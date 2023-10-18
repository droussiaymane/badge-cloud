import axios from 'axios';
import jwtDecode from 'jwt-decode';
import { isUndefined } from 'lodash';
import parse from '../utils/parse';

class AuthApi {
  async login(username, password) {
    const body = {
      username,
      password
    }
    const url = `${process.env.REACT_APP_AUTH_API}/user/login`
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
              const decoded = jwtDecode(res?.access_token);
              if (!decoded?.realm_access?.roles?.includes("Administrator")) {
                reject('Unauthorized user access');
              }
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

  async RefreshTokenApi(refreshToken) {
    const body = {
      refresh_token: refreshToken
    }
    const url = `${process.env.REACT_APP_AUTH_API}/user/refreshToken`
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
