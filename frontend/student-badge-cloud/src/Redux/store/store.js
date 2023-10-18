import { configureStore, combineReducers } from '@reduxjs/toolkit';
import standDardSlice from './Slices/standard';
import toastSlice from './Slices/toast';

import storage from 'redux-persist/lib/storage';
import {
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from 'redux-persist';
import standard from './Slices/standard';
import toast from './Slices/toast';
import sidebar from './Slices/sidebar';
import jwt from './Slices/userSlice';
import refreshTokenSlice from './Slices/refreshTokenSlice';
import { authenticationMiddleware, clientValidateActionMiddleware, validateActionMiddleware } from './Middleware/validationMiddleware';

const persistConfig = {
  key: { standard: standard, toast: toast, jwt: jwt, sidebar: sidebar },
  storage,
};

const reducers = combineReducers({
  standard: standDardSlice,
  toast: toastSlice,
  jwt: jwt,
  refresh: refreshTokenSlice,
  sidebar: sidebar
});
const persistedReducer = persistReducer(persistConfig, reducers);

export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
      // custom: [validateActionMiddleware, clientValidateActionMiddleware, authenticationMiddleware],
    }),
});
