import axios from '../utils/axios';
import { isUndefined } from 'lodash';
import parse from '../utils/parse';

class additionalDetailsApi {
  async create(values) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails`,
          values,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
            },
          }
        )
        .then((response) => {
          response = parse(response);
          console.log('response', response);
          if (response?.data?.code == 0) {
            reject(new Error(response?.data?.message));
          }
          else if (!isUndefined(response?.data)) {
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

  async edit(values) {
    console.log('Values', values);
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails`,
          values,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
            },
          }
        )
        .then((response) => {
          response = parse(response);
          if (response?.data?.code == 0) {
            reject(new Error(response?.data?.message));
          }
          else
            if (!isUndefined(response?.data)) {
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

  async getAll(tenantId, page, limit) {
    return new Promise((resolve, reject) => {
      axios
        .get(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails?page=${page}&limit=${limit}`,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
            },
          }
        )
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
        .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails/${id}`, {
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
        .delete(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails/${id}`,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
            },
          }
        )
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
        .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails/assign`, {
          headers: {
            'Access-Control-Allow-Origin': '*',
            tenantId: tenantId,
          },
        })
        .then((response) => {
          response = parse(response);
          console.log("additional Details", response);
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
        .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/AdditionalDetails/searchByName?name=${search}`, {
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

export const AdditionalDetailsApi = new additionalDetailsApi();
