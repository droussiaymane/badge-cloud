import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { Route, Routes } from 'react-router-dom';
import persistStore from 'redux-persist/es/persistStore';
import { PersistGate } from 'redux-persist/lib/integration/react';
import App from './App';
import { AuthProvider } from './context/AuthProvider';
import './index.css';
import { store } from './Redux/store/store';
import reportWebVitals from './reportWebVitals';
import { injectStore } from './utils/axios';

let persistor = persistStore(store);
const root = ReactDOM.createRoot(document.getElementById('root'));
injectStore(store);
root.render(
  <Provider store={store}>
    <PersistGate persistor={persistor}>
      {/* <AuthProvider> */}
      <App />
      {/* <Routes>
          <Route path="/*" element={<App />} />
        </Routes> */}
      {/* </AuthProvider> */}
    </PersistGate>
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
