import axios from '../utils/axios';
import { isUndefined } from 'lodash';
import parse from '../utils/parse';

class userApi {
  async create(values) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_USER_SERVICE_API}/users`,
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

  async edit(values, id) {
    return new Promise((resolve, reject) => {
      axios
        .put(
          `${process.env.REACT_APP_USER_SERVICE_API}/users/${id}`,
          values,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
            },
          }
        )
        .then((response) => {
          response = parse(response);
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

  async getAll(page, limit) {
    return new Promise((resolve, reject) => {
      axios
        .get(
          `${process.env.REACT_APP_USER_SERVICE_API}/users/all?page=${page}&limit=${limit}`,
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
        .get(`${process.env.REACT_APP_USER_SERVICE_API}/users/${id}`, {
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
          `${process.env.REACT_APP_USER_SERVICE_API}/users/${id}`,
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

  async getStudentPrograms(student_id) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${process.env.REACT_APP_USER_SERVICE_API}/users/students/programs/${student_id}`, {
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
  async getAssignRecords(tenantId) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${process.env.REACT_APP_USER_SERVICE_API}/users/assign`, {
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

  async UserCategoryByTenantId() {
    return new Promise((resolve, reject) => {
      axios
        .get(`${process.env.REACT_APP_USER_SERVICE_API}/category`, {
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

  async searchByName(search, categoryId) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${process.env.REACT_APP_USER_SERVICE_API}/users/searchByName?userName=${search}&category=${categoryId}`, {
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

  async getDashboardStatistics() {
    return new Promise((resolve, reject) => {
      axios
        .get(`${process.env.REACT_APP_USER_SERVICE_API}/dashboard`, {
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


  async createBulkUser(values, tenantId) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_USER_SERVICE_API}/users/bulk/${tenantId}`,
          values,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
              'Content-Type': 'multipart/form-data',
            },
          }
        )
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

  async assignBulkProgram(values, tenantId) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/students/assignProgram/bulk/${tenantId}`,
          values,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
              'Content-Type': 'multipart/form-data',
            },
          }
        )
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

}

export const UserApi = new userApi();
