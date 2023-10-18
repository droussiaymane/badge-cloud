import axios from 'axios';
const axiosInstance = axios.create();

let store;
export const injectStore = _store => {
    store = _store
}

// Request interceptor for API call
axiosInstance.interceptors.request.use(async (config) => {
    config.headers.Authorization = store.getState()?.jwt?.token ? `Bearer ${store.getState()?.jwt?.token}` : '';
    config.headers.tenantId = store?.getState()?.jwt?.tenantId ? store.getState().jwt.tenantId : '';
    return config;
});

// Response interceptor for API calls
// axiosInstance.interceptors.response.use(async (response) => response, async (error) => {
//     const originalRequest = error.config;
//     // eslint-disable-next-line no-underscore-dangle
//     if (error?.response?.status === 401 && !originalRequest._retry) {
//         // eslint-disable-next-line no-underscore-dangle
//         originalRequest._retry = true;
//         await wait(2500);
//         axios.defaults.headers.common.Authorization = store.getState()?.jwt?.access_token ? `Bearer ${store.getState()?.jwt?.access_token}` : 'Bearer dsksldjfslkdfjsldkfslkdfjslkdfjskdfjslfdj';
//         axios.defaults.headers.common.tenantId = store.getState()?.jwt?.access_token ? `Bearer ${store.getState()?.jwt?.access_token}` : 'Bearer dsksldjfslkdfjsldkfslkdfjslkdfjskdfjslfdj';
//         return axiosInstance(originalRequest);
//     }
//     return Promise.reject(error)
// });

export default axiosInstance;
