import axios from '../utils/axios';
import { isUndefined } from 'lodash';
import parse from '../utils/parse';

class programApi {
  async create(values) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs`,
          values,
          {
            headers: {
              'Access-Control-Allow-Origin': '*',
              createdId: 1,
            },
          }
        )
        .then((response) => {
          response = parse(response);
          console.log('response', response);
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

  async edit(values) {
    console.log('Values', values);
    return new Promise((resolve, reject) => {
      axios
        .put(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/${values.id}`,
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
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs?page=${page}&limit=${limit}`,
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
        .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/${id}`, {
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

  async getStudentByProgramId(id,page, limit) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/programStudents/${id}?page=${page}&limit=${limit}`, {
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
  async deleteStudentFromProgram(programId, studentId){
    return new Promise((resolve, reject) => {
      const body ={
        programId,
        studentId
      }
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/deleteStudentFromProgram`,
          body
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

  async disableStudentFromProgram(body){
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/enableOrDisableCollection`,
          body
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

  async updateRevokeStatusOrExpirationDate(body){
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/openBadges/updateExpirationRevoke`,
          body
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

  async assignProgram(currentProgramIds, assignedProgramIds, studentId){
    return new Promise((resolve, reject) => {
      const body ={
        currentProgramIds,
        assignedProgramIds,
        studentId
      }
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/students/assignProgram`,
          body
        )
        .then((response) => {
          response = parse(response);
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
  async deleteById(id) {
    return new Promise((resolve, reject) => {
      axios
        .delete(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/${id}`,
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

  async deleteProgramFromStudent(body) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/deleteStudentFromProgram`,
          body,
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
  async createBulkProgram(values, tenantId) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/bulk/${tenantId}`,
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
  async enableBulkCollection(values, tenantId) {
    return new Promise((resolve, reject) => {
      axios
        .post(
          `${process.env.REACT_APP_PROGRAM_SERVICE_API}/programs/enableOrDisableCollection/bulk/${tenantId}`,
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
export const ProgramApi = new programApi();
