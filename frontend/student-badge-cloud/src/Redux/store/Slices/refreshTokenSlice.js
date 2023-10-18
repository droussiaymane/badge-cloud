import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import jwtDecode from 'jwt-decode';
import { authApi } from '../../../api/authApi';

const initialState = {
    token: null,
    refreshToken: null,
    username: null,
    tenantId: null,
    sub: null,
    isSelected: false,
    isAuthenticated: false,
    isFailed: false,
    email: null,
    error: null,
};

export const refreshTokenApi = createAsyncThunk(
    "jwt/loginUser",
    async (user, { rejectWithValue }) => {
        const { refreshToken } = user;
        try {
            const res = await authApi.RefreshTokenApi(refreshToken);
            return res;
        }
        catch (error) {
            console.log(error)
            return rejectWithValue(error)
        }
    }
);

const tokenSlice = createSlice({
    name: 'jwt',
    initialState,
    reducers: {
        setJWT(state, action) {
            if (action?.payload) {
                const decoded = jwtDecode(action.payload.token);
                return {
                    ...state,
                    token: action.payload.token,
                    isSelected: action.payload.isSelected,
                    isAuthenticated: action.payload.isAuthenticated,
                    tenantId: decoded?.tenantId,
                    sub: decoded?.sub,
                    email: decoded?.email,
                }
            } else return state;
        },
        clearJWT(state, action) {
            state.token = null
            state.refreshToken = null
            state.error = null
            state.tenantId = null
            state.sub = null
            state.isSelected = true
            state.isAuthenticated = false
            state.isFailed = false
            state.email = null
            state.user = null
            state.isFailed = false
        },
    },
    extraReducers: (builder) => {
        builder.addCase(refreshTokenApi.pending, (state, action) => {
            return { ...state, isSelected: true, isFailed: true };
        }).addCase(refreshTokenApi.fulfilled, (state, action) => {
            if (action?.payload) {
                const decoded = jwtDecode(action?.payload?.access_token);
                return {
                    ...state,
                    token: action?.payload?.access_token,
                    refreshToken: action?.payload?.refresh_token,
                    sub: decoded.sub,
                    email: decoded.email,
                    username: decoded.name,
                    tenantId: decoded.tenantId,
                    isSelected: false,
                    isFailed: false,
                    isAuthenticated: true
                };
            }
            else return state;

        }).addCase(refreshTokenApi.rejected, (state, action) => {
            if (action.payload) {
                return {
                    ...state,
                    isAuthenticated: false,
                    isSelected: false,
                    isFailed: false,
                    error: action.payload
                }
            }
        });
    }
});
export default tokenSlice.reducer;