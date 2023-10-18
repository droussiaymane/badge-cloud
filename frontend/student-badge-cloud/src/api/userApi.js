import { isUndefined } from 'lodash';
import axios from '../utils/axios';
import parse from '../utils/parse';

class StudentApi {
    async getSingleStudent(id) {

        const url = `${process.env.REACT_APP_USERS_API_URL}/users/students/home/${id}`
        console.log("getStudent", id, url)
        return new Promise((resolve, reject) => {
            axios
                .get(url)
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

    async getUserByKid(id) {
        const url = `${process.env.REACT_APP_USERS_API_URL}/users/getKUser/${id}`;
        console.log("url", url);
        return new Promise((resolve, reject) => {
            axios
                .get(url)
                .then((response) => {
                    response = parse(response);
                    if (!isUndefined(response)) {
                        const res = response?.data;
                        console.log("ressdfs", res)
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

    async getSingleStudentProfile(id) {
        const url = `${process.env.REACT_APP_USERS_API_URL}/users/userProfile/${id}`
        return new Promise((resolve, reject) => {
            axios
                .get(url)
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
    async updateSingleStudentProfile(body, id) {
        const url = `${process.env.REACT_APP_USERS_API_URL}/users/updateUserProfile/${id}`;
        console.log("called", url)
        return new Promise((resolve, reject) => {
            axios
                .post(url, body)
                .then((response) => {
                    response = parse(response);
                    console.log("success", response)
                    if (!isUndefined(response)) {
                        const res = response?.data;
                        resolve(res);
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


    async getSingleBadge(id) {
        const url = `${process.env.REACT_APP_BADGE_API_URL}/${id}`
        return new Promise((resolve, reject) => {
            axios
                .get(url)
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

    async getEarnedBadges(id) {
        const url = `${process.env.REACT_APP_PROGRAM_API_URL}/programs/badge/students/${id}`
        return new Promise((resolve, reject) => {
            axios
                .get(url)
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

    async getEarnedOpenBadgeView(studentId,programId) {
        const url = `${process.env.REACT_APP_PROGRAM_API_URL}/openBadges/studentOpenBadgeView?studentId=${studentId}&programId=${programId}`
        return new Promise((resolve, reject) => {
            axios
                .get(url)
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

    async downloadOpenBadge(studentId, programId) {
        const downloadUrl  = `${process.env.REACT_APP_PROGRAM_API_URL}/openBadges/downloadStudentOpenBadge?studentId=${studentId}&programId=${programId}`;
        try {
            const response = await axios.get(downloadUrl , { responseType: 'blob' });
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'badge.png');
            document.body.appendChild(link);
            link.click();
            link.remove();
        } catch (error) {
            console.error(error);
            console.error('Hellllloooooooo : '+error);
        }
    }


    async getPublicUrl(body) {
        const url = `${process.env.REACT_APP_PROGRAM_API_URL}/public/userBadgeViewUrl`
        return new Promise((resolve, reject) => {
            axios
                .post(url, body)
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
}

export const studentApi = new StudentApi();
