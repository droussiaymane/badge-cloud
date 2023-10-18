import axios from 'axios';
import { isUndefined } from 'lodash';
import parse from '../utils/parse';

class SocialMediaSharingBadge {
    async getSocialMediaBadge(body) {
        const url = `${process.env.REACT_APP_PUBLIC_BADGE_URL}/userBadgeViewUrl`
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
    async getSocialMediaBadgeUrl(id) {
        const url = `${process.env.REACT_APP_PROGRAM_API_URL}/public?publicViewId=${id}`
        return new Promise((resolve, reject) => {
            axios
                .get(url,)
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

export const socialMediaBadgeApi = new SocialMediaSharingBadge();
